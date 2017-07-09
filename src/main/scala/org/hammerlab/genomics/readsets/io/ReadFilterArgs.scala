package org.hammerlab.genomics.readsets.io

import org.hammerlab.args4s.IntOptionHandler
import org.hammerlab.bytes.{ Bytes, BytesOptionHandler }
import org.hammerlab.genomics.loci.args.LociArgs
import org.kohsuke.args4j.{ Option ⇒ Args4jOption }

trait ReadFilterArgs
  extends ReadFilters
    with LociArgs {

  @Args4jOption(
    name = "--min-alignment-quality",
    usage = "Minimum read mapping quality for a read (Phred-scaled)",
    handler = classOf[IntOptionHandler]
  )
  var minAlignmentQualityOpt: Option[Int] = None

  @Args4jOption(
    name = "--include-duplicates",
    usage = "Include reads marked as duplicates"
  )
  var includeDuplicates: Boolean = false

  @Args4jOption(
    name = "--include-failed-quality-checks",
    usage = "Include reads that failed vendor quality checks"
  )
  var includeFailedQualityChecks: Boolean = false

  @Args4jOption(
    name = "--include-single-end",
    usage = "Include single-end reads"
  )
  var includeSingleEnd: Boolean = false

  @Args4jOption(
    name = "--only-mapped-reads",
    usage = "Include only mapped reads",
    forbids = Array("--loci", "--loci-file")
  )
  var onlyMappedReads: Boolean = false

  @Args4jOption(
    name = "--split-size",
    usage = "Maximum HDFS split size",
    handler = classOf[BytesOptionHandler]
  )
  var splitSizeOpt: Option[Bytes] = None
}
