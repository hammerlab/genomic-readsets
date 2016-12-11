package org.hammerlab.genomics.readsets.rdd

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.hammerlab.genomics.reads.{ MappedRead, ReadsUtil }
import org.hammerlab.genomics.readsets.args.SingleSampleArgs
import org.hammerlab.genomics.readsets.io.{ InputConfig, TestInputConfig }
import org.hammerlab.genomics.readsets.{ ReadSets, SampleId }
import org.hammerlab.test.resources.File

trait ReadsRDDUtil
  extends ReadsUtil {

  def sc: SparkContext

  def makeReadsRDD(reads: (String, String, Int)*): RDD[MappedRead] = makeReadsRDD(sampleId = 0, reads: _*)

  def makeReadsRDD(sampleId: SampleId, reads: (String, String, Int)*): RDD[MappedRead] =
    sc.parallelize(
      for {
        (sequence, cigar, start) <- reads
      } yield
        makeRead(sequence, cigar, start)
    )

  def loadTumorNormalReads(sc: SparkContext,
                           tumorFile: String,
                           normalFile: String): (Seq[MappedRead], Seq[MappedRead]) = {
    val config = TestInputConfig.mapped(nonDuplicate = true, passedVendorQualityChecks = true)
    (
      loadReadsRDD(sc, tumorFile, config = config).mappedReads.collect(),
      loadReadsRDD(sc, normalFile, config = config).mappedReads.collect()
    )
  }

  def loadReadsRDD(sc: SparkContext,
                   filename: String,
                   config: InputConfig = InputConfig.empty): ReadsRDD = {
    assert(sc != null)
    assert(sc.hadoopConfiguration != null)
    val args = new SingleSampleArgs {}

    // Load resource File.
    args.reads = File(filename)

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
