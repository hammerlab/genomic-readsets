package org.hammerlab.genomics.readsets.io

import org.apache.hadoop.conf.Configuration
import org.hammerlab.genomics.loci.parsing.{ All, ParsedLoci }
import org.hammerlab.hadoop.{ FileSplits, MaxSplitSize }

/**
 * Configuring how/which reads are loaded can be an important optimization.
 *
 * Most of these fields are commonly used filters. For boolean fields, setting a field to true will result in filtering
 * on that field. The result is the intersection of the filters (i.e. reads must satisfy ALL filters).
 *
 * @param overlapsLoci if set, include only mapped reads that overlap the given loci
 * @param nonDuplicate include only reads that do not have the duplicate bit set
 * @param passedVendorQualityChecks include only reads that do not have the failedVendorQualityChecks bit set
 * @param isPaired include only reads are paired-end reads
 * @param minAlignmentQuality Minimum Phred-scaled alignment score for a read
 * @param maxSplitSize Maximum on-disk size to allow Hadoop splits to be; useful to set below the on-disk block-size
 *                        for BAMs where the reads compress extra-well, resulting in unwieldy numbers of reads per
 *                        fixed-disk-size partition.
 */
case class Config(overlapsLoci: Option[ParsedLoci],
                  nonDuplicate: Boolean,
                  passedVendorQualityChecks: Boolean,
                  isPaired: Boolean,
                  minAlignmentQuality: Option[Int],
                  maxSplitSize: MaxSplitSize)
  extends FileSplits.Config {
  def loci = overlapsLoci.getOrElse(All)
}

object Config {
  def empty(implicit conf: Configuration) =
    Config(
      overlapsLoci = None,
      nonDuplicate = false,
      passedVendorQualityChecks = false,
      isPaired = false,
      minAlignmentQuality = None,
      maxSplitSize = MaxSplitSize()
    )
}
