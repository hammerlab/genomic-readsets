package org.hammerlab.genomics.readsets.args.impl

import org.hammerlab.genomics.readsets.args.base.PrefixedPathsBase
import org.hammerlab.genomics.readsets.args.path.{ UnprefixedPath, UnprefixedPathHandler }
import org.kohsuke.args4j.Argument

trait PathsArgs
  extends PathPrefixArg {
    self: PrefixedPathsBase ⇒
    @Argument(
      required = true,
      multiValued = true,
      handler = classOf[UnprefixedPathHandler],
      usage = "Paths to sets of reads: FILE1 FILE2 FILE3",
      metaVar = "PATHS"
    )
    var unprefixedPaths: Array[UnprefixedPath] = Array()
  }
