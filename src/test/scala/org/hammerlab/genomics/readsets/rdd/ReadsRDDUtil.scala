package org.hammerlab.genomics.readsets.rdd

import java.nio.file.Path

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.hammerlab.genomics.bases.Bases
import org.hammerlab.genomics.reads.{ MappedRead, ReadsUtil }
import org.hammerlab.genomics.readsets.args.impl.SingleSampleArgs
import org.hammerlab.genomics.readsets.io.{ InputConfig, TestInputConfig }
import org.hammerlab.genomics.readsets.{ ReadSets, SampleId, SampleRead }
import org.hammerlab.genomics.reference.Locus
import org.hammerlab.test.resources.PathUtil

trait ReadsRDDUtil
  extends ReadsUtil
    with PathUtil {

  def sc: SparkContext

  def makeReadsRDD(reads: (Bases, String, Locus)*): RDD[SampleRead] = makeReadsRDD(sampleId = 0, reads: _*)

  def makeReadsRDD(sampleId: SampleId, reads: (Bases, String, Locus)*): RDD[SampleRead] =
    sc.parallelize(
      for {
        (sequence, cigar, start) ← reads
      } yield
        SampleRead(sampleId → makeRead(sequence, cigar, start))
    )

  def loadTumorNormalReads(sc: SparkContext,
                           tumorPath: Path,
                           normalPath: Path): (Seq[MappedRead], Seq[MappedRead]) = {
    val config = TestInputConfig.mapped(nonDuplicate = true, passedVendorQualityChecks = true)
    (
      loadReadsRDD(sc,  tumorPath, config = config).mappedReads.collect(),
      loadReadsRDD(sc, normalPath, config = config).mappedReads.collect()
    )
  }

  def loadReadsRDD(sc: SparkContext,
                   path: Path,
                   config: InputConfig = InputConfig.empty): ReadsRDD = {
    assert(sc != null)
    assert(sc.hadoopConfiguration != null)
    val args = new SingleSampleArgs {}

    // Load resource File.
    args.reads = path

    val ReadSets(reads, _, _) =
      ReadSets(
        sc,
        args.inputs,
        config,
        contigLengthsFromDictionary = !args.noSequenceDictionary
      )

    reads(0)
  }
}
