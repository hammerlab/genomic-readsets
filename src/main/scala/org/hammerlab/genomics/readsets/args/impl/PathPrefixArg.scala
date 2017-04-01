package org.hammerlab.genomics.readsets.args.impl

import org.hammerlab.genomics.readsets.args.base.PrefixedPathsBase
import org.hammerlab.genomics.readsets.args.path.{ PathPrefix, PathPrefixOptionHandler }
import org.kohsuke.args4j

trait PathPrefixArg
  extends PrefixedPathsBase {
  @args4j.Option(
    name = "--dir",
    aliases = Array("-d"),
    handler = classOf[PathPrefixOptionHandler],
    usage = "When set, relative paths will be prefixed with this path"
  )
  implicit var pathPrefixOpt: Option[PathPrefix] = None
}
