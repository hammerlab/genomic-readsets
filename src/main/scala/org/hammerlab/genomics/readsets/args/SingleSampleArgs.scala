package org.hammerlab.genomics.readsets.args

import caseapp.{ HelpMessage ⇒ M, Recurse ⇒ R }
import hammerlab.path._
import org.hammerlab.genomics.readsets.args.Base
import org.hammerlab.genomics.readsets.args.path.UnprefixedPath
import org.hammerlab.genomics.readsets.io.ReadFilterArgs
import org.hammerlab.genomics.readsets.io.Sample.Name

/** Argument for accepting a single set of reads (for e.g. germline variant calling). */
case class SingleSampleArgs(
  @R readFilterArgs: ReadFilterArgs = ReadFilterArgs(),
  @R noSequenceDictionaryArgs: NoSequenceDictionaryArgs = NoSequenceDictionaryArgs(),
  @M("Path to aligned reads")
  reads: Path
) extends Base {
  override def       paths: Array[Path] = Array( reads )
  override def sampleNames: Array[Name] = Array("reads")
}

/** [[SingleSampleArgs]] implementation that supports path-prefixing. */
case class PrefixedSingleSampleArgs(
  @R readFilterArgs: ReadFilterArgs,
  @R noSequenceDictionaryArgs: NoSequenceDictionaryArgs,
  @R prefix: PathPrefixArg,
  @M("Path to aligned reads")
  reads: UnprefixedPath
) extends Base {
  override def       paths: Array[Path] = Array( reads.buildPath(prefix.dir) )
  override def sampleNames: Array[Name] = Array("reads")
}
