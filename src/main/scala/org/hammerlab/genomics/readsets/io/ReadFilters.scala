package org.hammerlab.genomics.readsets.io

import org.apache.hadoop.conf.Configuration
import org.apache.spark.network.util.JavaUtils.byteStringAsBytes
import org.hammerlab.genomics.loci.parsing.{ All, ParsedLoci }

trait ReadFilters {

  def lociStrOpt: Option[String]
  def lociFileOpt: Option[String]

  def onlyMappedReads: Boolean
  def includeDuplicates: Boolean
  def includeFailedQualityChecks: Boolean
  def includeSingleEnd: Boolean
  def minAlignmentQualityOpt: Option[Int]
  def splitSizeOpt: Option[String]

  def parseConfig(hadoopConfiguration: Configuration): InputConfig = {
    val loci = ParsedLoci.fromArgs(lociStrOpt, lociFileOpt, hadoopConfiguration)
    InputConfig(
      overlapsLociOpt =
        if (onlyMappedReads)
          Some(All)
        else
          loci,
      nonDuplicate = !includeDuplicates,
      passedVendorQualityChecks = !includeFailedQualityChecks,
      isPaired = !includeSingleEnd,
      minAlignmentQualityOpt = minAlignmentQualityOpt,
      maxSplitSizeOpt = splitSizeOpt.map(byteStringAsBytes)
    )
  }
}