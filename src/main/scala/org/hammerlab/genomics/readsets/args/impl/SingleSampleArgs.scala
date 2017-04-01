package org.hammerlab.genomics.readsets.args.impl

import java.nio.file.Path

import org.hammerlab.genomics.readsets.args.base.{ Base, PrefixedPathsBase }
import org.hammerlab.genomics.readsets.args.path.{ UnprefixedPath, UnprefixedPathHandler }
import org.hammerlab.genomics.readsets.io.ReadFilterArgs
import org.kohsuke.args4j.{ Option ⇒ Args4jOption }

/** Argument for accepting a single set of reads (for e.g. germline variant calling). */
trait SingleSampleArgsI
  extends Base
    with NoSequenceDictionaryArgs
    with ReadFilterArgs {
  def sampleName = "reads"
  override def sampleNames = Array(sampleName)
}

/** [[SingleSampleArgsI]] implementation that does not support path-prefixing. */
class SingleSampleArgs
  extends SingleSampleArgsI {
  var reads: Path = _
  override def paths: Array[Path] = Array(reads)
}

/** [[SingleSampleArgsI]] implementation that supports path-prefixing. */
trait PrefixedSingleSampleArgs
  extends SingleSampleArgsI
    with PrefixedPathsBase {
  @Args4jOption(
    name = "--reads",
    metaVar = "PATH",
    required = true,
    handler = classOf[UnprefixedPathHandler],
    usage = "Path to aligned reads"
  )
  var reads: UnprefixedPath = _
  override protected def unprefixedPaths: Array[UnprefixedPath] = Array(reads)
}
