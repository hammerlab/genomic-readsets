package org.hammerlab.genomics.readsets

import grizzled.slf4j.Logging
import hammerlab.path._
import htsjdk.samtools.ValidationStringency
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.bdgenomics.adam.models.SequenceDictionary
import org.bdgenomics.adam.rdd.ADAMContext
import org.hammerlab.bam
import org.hammerlab.genomics.loci.parsing.All
import org.hammerlab.genomics.loci.set.LociSet
import org.hammerlab.genomics.reads.Read
import org.hammerlab.genomics.readsets.args.Base
import org.hammerlab.genomics.readsets.io.{ Config, Input, Sample }
import org.hammerlab.genomics.readsets.rdd.ReadsRDD
import org.hammerlab.genomics.reference.{ ContigLengths, ContigName, Locus }
import org.hammerlab.spark.Context
import spark_bam._

/**
 * A [[ReadSets]] contains reads from multiple inputs as well as [[SequenceDictionary]] / contig-length information
 * merged from them.
 */
case class ReadSets(readsRDDs: PerSample[ReadsRDD],
                    sequenceDictionary: SequenceDictionary,
                    contigLengths: ContigLengths) {

  def samples: PerSample[Sample] = readsRDDs.map(_.sample)

  def numSamples: NumSamples = readsRDDs.length
  def sampleNames: PerSample[String] = samples.map(_.name)

  def sc = readsRDDs.head.reads.sparkContext

  lazy val mappedReadsRDDs = readsRDDs.map(_.mappedReads)

  lazy val allMappedReads = sc.union(mappedReadsRDDs).setName("unioned reads")

  lazy val sampleIdxKeyedMappedReads: RDD[SampleRead] =
    sc.union(
      for {
        (mappedReadsRDD, sampleId) ← mappedReadsRDDs.zipWithIndex
      } yield
        mappedReadsRDD.map(r ⇒ (sampleId → r): SampleRead)
    )
}

object ReadSets extends Logging {

  implicit def toRDDs(readsets: ReadSets): PerSample[ReadsRDD] = readsets.readsRDDs

  import hammerlab.shapeless._
  def apply(args: Base)(
      implicit
      sc: SparkContext,
      cf: ContigName.Factory
  ): (ReadSets, LociSet) = {
    val config = args.readFilterArgs.parseConfig(sc.hadoopConfiguration)
    val readsets =
      apply(
        args.inputs,
        config,
        !args.noSequenceDictionaryArgs.noSequenceDictionary
      )
    (
      readsets,
      LociSet(
        config.loci,
        readsets.contigLengths
      )
    )
  }

  /**
    * Load reads from multiple files, merging their sequence dictionaries and verifying that they are consistent.
    */
  def apply(inputs: Inputs,
            config: Config,
            contigLengthsFromDictionary: Boolean = true)(
      implicit
      sc: SparkContext,
      cf: ContigName.Factory
  ): ReadSets =
    apply(
      inputs.map((_, config)),
      contigLengthsFromDictionary
    )

  /**
   * Load reads from multiple files, allowing different filters to be applied to each file.
   */
  def apply(inputsAndFilters: PerSample[(Input, Config)],
            contigLengthsFromDictionary: Boolean)(
      implicit
      sc: SparkContext,
      cf: ContigName.Factory
  ): ReadSets = {

    val (inputs, _) = inputsAndFilters.unzip

    val (readsRDDs, sequenceDictionaries) =
      (for {
        (Input(id, _, path), config) ← inputsAndFilters
      } yield
        load(path, id, config)
      )
      .unzip

    val sequenceDictionary = mergeSequenceDictionaries(inputs, sequenceDictionaries)

    val contigLengths: ContigLengths =
      if (contigLengthsFromDictionary)
        getContigLengthsFromSequenceDictionary(sequenceDictionary)
      else
        sc.union(readsRDDs)
          .flatMap(_.asMappedRead)
          .map(read ⇒ read.contigName → read.end)
          .reduceByKey(_ max _)
          .collectAsMap()
          .toMap

    ReadSets(
      (for {
        (reads, input) ← readsRDDs.zip(inputs)
      } yield
       ReadsRDD(reads, input)
      )
      .toVector,
      sequenceDictionary,
      contigLengths
    )
  }

  def apply(readsRDDs: PerSample[ReadsRDD], sequenceDictionary: SequenceDictionary): ReadSets =
    ReadSets(
      readsRDDs,
      sequenceDictionary,
      getContigLengths(sequenceDictionary)
    )

  /**
   * Given a filename and a spark context, return a pair (RDD, SequenceDictionary), where the first element is an RDD
   * of Reads, and the second element is the Sequence Dictionary giving info (e.g. length) about the contigs in the BAM.
   *
   * @param path name of file containing reads
   * @param sc spark context
   * @param config config to apply
   * @return
   */
  private[readsets] def load(path: Path,
                             sampleId: Int,
                             config: Config)(
      implicit
      sc: SparkContext,
      cf: ContigName.Factory
  ): (RDD[Read], SequenceDictionary) = {

    val (allReads, sequenceDictionary) =
      if (path.toString.endsWith(".bam") || path.toString.endsWith(".sam"))
        loadFromBAM(path, sampleId, config)
      else
        loadFromADAM(path, sampleId, config)

    val reads = filterRDD(allReads, config, sequenceDictionary)

    (reads, sequenceDictionary)
  }

  /** Returns an RDD of Reads and SequenceDictionary from reads in BAM format **/
  private def loadFromBAM(path: Path,
                          sampleId: Int,
                          config: Config)(
      implicit
      sc: Context,
      cf: ContigName.Factory
  ): (RDD[Read], SequenceDictionary) = {

    val contigLengths = bam.header.ContigLengths(path)

    val sequenceDictionary = SequenceDictionary(contigLengths)

    implicit val splitSize = config.maxSplitSize

    val reads =
      config
        .overlapsLoci
        .filterNot(_ == All)
        .fold {
          sc
            .loadReads(
              path,
              splitSize = config.maxSplitSize
            )
        } {
          loci ⇒
            sc
              .loadBamIntervals(
                path,
                LociSet(
                  loci,
                  contigLengths.values.toMap
                )
              )
        }
        .map(Read(_))

    (reads, sequenceDictionary)
  }

  /** Returns an RDD of Reads and SequenceDictionary from reads in ADAM format **/
  private def loadFromADAM(path: Path,
                           sampleId: Int,
                           config: Config)(
      implicit
      sc: SparkContext,
      cf: ContigName.Factory
  ): (RDD[Read], SequenceDictionary) = {

    logger.info(s"Using ADAM to read: $path")

    import ADAMContext._

    val alignmentRDD = sc.loadAlignments(path, stringency = ValidationStringency.LENIENT)

    val sequenceDictionary = alignmentRDD.sequences

    (alignmentRDD.rdd.map(Read(_, sampleId)), sequenceDictionary)
  }


  /** Extract the length of each contig from a sequence dictionary */
  private def getContigLengths(sequenceDictionary: SequenceDictionary): ContigLengths = {
    val builder = Map.newBuilder[ContigName, Locus]
    sequenceDictionary.records.foreach(record => builder += ((record.name.toString, record.length)))
    builder.result
  }

  /**
   * SequenceDictionaries store information about the contigs that will be found in a given set of reads: names,
   * lengths, etc.
   *
   * When loading/manipulating multiple sets of reads, we generally want to understand the set of all contigs that
   * are referenced by the reads, perform some consistency-checking (e.g. verifying that each contig is listed as having
   * the same length in each set of reads in which it appears), and finally pass the downstream user a
   * SequenceDictionary that encapsulates all of this.
   *
   * This function performs all of the above.
   *
   * @param inputs Input files, each containing a set of reads.
   * @param dicts SequenceDictionaries that have been parsed from @filenames.
   * @return a SequenceDictionary that has been merged and validated from the inputs.
   */
  private[readsets] def mergeSequenceDictionaries(inputs: Inputs,
                                                  dicts: Seq[SequenceDictionary]): SequenceDictionary = {
    val records =
      (for {
        (input, dict) ← inputs.zip(dicts)
        record ← dict.records
      } yield
        input → record
      )
      .groupBy(_._2.name)
      .values
      .map { values ⇒
        val (input, record) = values.head

        // Verify that all records for a given contig are equal.
        values.tail.toList.filter(_._2 != record) match {
          case Nil ⇒
          case mismatched ⇒
            throw new IllegalArgumentException(
              (
                s"Conflicting sequence records for ${record.name}:" ::
                s"$input: $record" ::
                mismatched.map { case (otherFile, otherRecord) => s"$otherFile: $otherRecord" }
              )
              .mkString("\n\t")
            )
        }

        record
      }

    new SequenceDictionary(records.toVector).sorted
  }

  /**
   * Apply filters to an RDD of reads.
   */
  private def filterRDD(reads: RDD[Read], config: Config, sequenceDictionary: SequenceDictionary): RDD[Read] = {
    /* Note that the InputFilter properties are public, and some loaders directly apply
     * the filters as the reads are loaded, instead of filtering an existing RDD as we do here. If more filters
     * are added, be sure to update those implementations.
     *
     * This is implemented as a static function instead of a method in InputConfig because the overlapsLoci
     * attribute cannot be serialized.
     */
    var result = reads

    config
    .overlapsLoci
      .foreach { overlapsLoci ⇒
        val contigLengths = getContigLengths(sequenceDictionary)
        val loci = LociSet(overlapsLoci, contigLengths)
        val broadcastLoci = reads.sparkContext.broadcast(loci)
        result = result.filter(_.asMappedRead.exists(broadcastLoci.value.intersects))
      }

    if (config.nonDuplicate) result = result.filter(!_.isDuplicate)
    if (config.passedVendorQualityChecks) result = result.filter(!_.failedVendorQualityChecks)
    if (config.isPaired) result = result.filter(_.isPaired)

    config.minAlignmentQuality.foreach(
      minAlignmentQuality ⇒
        result =
          result.filter(
            _.asMappedRead
             .forall(_.alignmentQuality >= minAlignmentQuality)
          )
    )

    result
  }

  /**
    * Construct a map from contig name -> length of contig, using a SequenceDictionary.
    */
  private def getContigLengthsFromSequenceDictionary(sequenceDictionary: SequenceDictionary): ContigLengths = {
    val builder = Map.newBuilder[ContigName, Locus]
    for {
      record <- sequenceDictionary.records
    } {
      builder += ((record.name, record.length))
    }
    builder.result
  }
}
