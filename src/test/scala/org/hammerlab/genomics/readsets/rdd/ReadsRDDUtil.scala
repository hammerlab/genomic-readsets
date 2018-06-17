package org.hammerlab.genomics.readsets.rdd

import hammerlab.path._
import org.apache.spark.rdd.RDD
import org.hammerlab.genomics.bases.Bases
import org.hammerlab.genomics.reads.{ MappedRead, ReadsUtil }
import org.hammerlab.genomics.readsets.args.SingleSampleArgs
import org.hammerlab.genomics.readsets.io.{ Config, TestInputConfig }
import org.hammerlab.genomics.readsets.{ ReadSets, SampleId, SampleRead }
import org.hammerlab.genomics.reference.Locus
import org.hammerlab.spark.test.suite.SparkSuite
import org.hammerlab.test.resources.PathUtil

trait ReadsRDDUtil
  extends ReadsUtil
    with PathUtil {

  self: SparkSuite ⇒

  def makeReadsRDD(reads: (Bases, String, Locus)*): RDD[SampleRead] = makeReadsRDD(sampleId = 0, reads: _*)

  def makeReadsRDD(sampleId: SampleId, reads: (Bases, String, Locus)*): RDD[SampleRead] =
    sc.parallelize(
      for {
        (sequence, cigar, start) ← reads
      } yield
        SampleRead(sampleId → makeRead(sequence, cigar, start))
    )

  def loadTumorNormalReads(tumorPath: Path,
                           normalPath: Path): (Seq[MappedRead], Seq[MappedRead]) = {
    val config = TestInputConfig.mapped(nonDuplicate = true, passedVendorQualityChecks = true)
    (
      loadReadsRDD( tumorPath, config).mappedReads.collect(),
      loadReadsRDD(normalPath, config).mappedReads.collect()
    )
  }

  def loadReadsRDD(path: Path,
                   config: Config = Config.empty): ReadsRDD = {
    val args: SingleSampleArgs =
      SingleSampleArgs(
        reads = path
      )

    val inputs = args.inputs

    val ReadSets(reads, _, _) =
      ReadSets(
        inputs,
        config,
        contigLengthsFromDictionary = !args.noSequenceDictionaryArgs.noSequenceDictionary
      )

    reads(0)
  }
}
