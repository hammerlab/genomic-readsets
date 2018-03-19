package org.hammerlab.genomics.readsets.args

import caseapp.{ HelpMessage ⇒ M, Recurse ⇒ R }
import org.hammerlab.genomics.readsets.args.path.UnprefixedPath

case class PathsArgs(
    @R prefix: PathPrefixArg,

    @M("Paths to sets of reads: FILE1 FILE2 FILE3")
    unprefixedPaths: Array[UnprefixedPath] = Array()
)
