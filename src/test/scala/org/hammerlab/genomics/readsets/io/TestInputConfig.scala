package org.hammerlab.genomics.readsets.io

import org.hammerlab.genomics.loci.parsing.{ All, ParsedLoci }
import org.hammerlab.hadoop.Configuration
import org.hammerlab.hadoop.splits.MaxSplitSize

object TestInputConfig {
  def mapped(nonDuplicate: Boolean = false,
             passedVendorQualityChecks: Boolean = false,
             isPaired: Boolean = false,
             minAlignmentQuality: Int = 0)(
      implicit conf: Configuration
  ): Config =
    new Config(
      overlapsLoci = Some(All),
      nonDuplicate,
      passedVendorQualityChecks,
      isPaired,
      if (minAlignmentQuality > 0)
        Some(minAlignmentQuality)
      else
        None,
      maxSplitSize = MaxSplitSize()
    )

  def apply(overlapsLoci: ParsedLoci,
            nonDuplicate: Boolean = false,
            passedVendorQualityChecks: Boolean = false,
            isPaired: Boolean = false,
            minAlignmentQuality: Int = 0)(
      implicit conf: Configuration
  ): Config =
    new Config(
      overlapsLoci = Some(overlapsLoci),
      nonDuplicate,
      passedVendorQualityChecks,
      isPaired,
      if (minAlignmentQuality > 0)
        Some(minAlignmentQuality)
      else
        None,
      maxSplitSize = MaxSplitSize()
    )
}
