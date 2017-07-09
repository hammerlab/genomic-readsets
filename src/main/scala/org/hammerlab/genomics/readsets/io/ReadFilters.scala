package org.hammerlab.genomics.readsets.io

import org.hammerlab.bytes.Bytes
import org.hammerlab.genomics.loci.args.LociInput
import org.hammerlab.genomics.loci.parsing.{ All, ParsedLoci }
import org.hammerlab.hadoop.Configuration
import org.hammerlab.hadoop.splits.MaxSplitSize

trait ReadFilters
  extends LociInput {

  def onlyMappedReads: Boolean
  def includeDuplicates: Boolean
  def includeFailedQualityChecks: Boolean
  def includeSingleEnd: Boolean
  def minAlignmentQualityOpt: Option[Int]
  def splitSizeOpt: Option[Bytes]

  def parseConfig(implicit conf: Configuration): Config = {
    val loci = ParsedLoci(lociStrOpt, lociFileOpt)
    Config(
      overlapsLoci =
        if (onlyMappedReads)
          Some(All)
        else
          loci,
      nonDuplicate = !includeDuplicates,
      passedVendorQualityChecks = !includeFailedQualityChecks,
      isPaired = !includeSingleEnd,
      minAlignmentQuality = minAlignmentQualityOpt,
      maxSplitSize =
        MaxSplitSize(
          splitSizeOpt
        )
    )
  }
}
