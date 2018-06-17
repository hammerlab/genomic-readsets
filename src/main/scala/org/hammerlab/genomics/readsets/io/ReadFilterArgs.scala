package org.hammerlab.genomics.readsets.io

import caseapp.{ HelpMessage ⇒ M, Recurse ⇒ R }
import hammerlab.bytes._
import hammerlab.option._
import org.hammerlab.genomics.loci
import org.hammerlab.genomics.loci.parsing.All
import org.hammerlab.hadoop.Configuration
import org.hammerlab.hadoop.splits.MaxSplitSize

case class ReadFilterArgs(
    @R lociArgs: loci.Args = loci.Args(),

    @M("Minimum read mapping quality for a read (Phred-scaled)")
    minAlignmentQuality: Option[Int] = None,

    @M("Include reads marked as duplicates")
    includeDuplicates: Boolean = false,

    @M("Include reads that failed vendor quality checks")
    includeFailedQualityChecks: Boolean = false,

    @M("Include single-end reads")
    includeSingleEnd: Boolean = false,

    // TODO: forbid --loci, --loci-file
    @M("Include only mapped reads")
    onlyMappedReads: Boolean = false,

    @M("Maximum HDFS split size")
    splitSize: Option[Bytes] = None
) {
  def parseConfig(implicit conf: Configuration): Config = {
    val parsed =
      (lociArgs.loci, lociArgs.lociFile, onlyMappedReads) match {
        case (None, None, _) ⇒
          onlyMappedReads ? All
        case (_, _, true) ⇒
          throw new IllegalArgumentException(
            s"Can't specify --only-mapped-reads *and* (--loci || --loci-file)"
          )
        case _ ⇒
          lociArgs.parse
      }
    Config(
      overlapsLoci = parsed,
      nonDuplicate = !includeDuplicates,
      passedVendorQualityChecks = !includeFailedQualityChecks,
      isPaired = !includeSingleEnd,
      minAlignmentQuality = minAlignmentQuality,
      maxSplitSize =
        MaxSplitSize(
          splitSize
        )
    )
  }
}
