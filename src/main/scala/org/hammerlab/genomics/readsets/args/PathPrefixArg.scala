package org.hammerlab.genomics.readsets.args

import caseapp.{ HelpMessage ⇒ M }
import org.hammerlab.genomics.readsets.args.path.Prefix

case class PathPrefixArg(
  @M("When set, relative paths will be prefixed with this path")
  dir: Option[Prefix] = None
)
