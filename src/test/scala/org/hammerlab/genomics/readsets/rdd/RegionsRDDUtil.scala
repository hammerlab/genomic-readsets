package org.hammerlab.genomics.readsets.rdd

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.hammerlab.genomics.reference.Region
import org.hammerlab.genomics.reference.test.region._

trait RegionsRDDUtil {

  def sc: SparkContext

  def makeRegionsRDD(numPartitions: Int, reads: (String, Int, Int, Int)*): RDD[Region] =
    sc.parallelize[Region](reads, numPartitions)
}
