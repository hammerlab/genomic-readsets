package org.hammerlab.genomics.readsets.args.base

import java.nio.file.Path

import org.hammerlab.genomics.readsets.args.path.{ PathPrefix, UnprefixedPath }

trait PrefixedPathsBase
  extends Base {
  protected def unprefixedPaths: Array[UnprefixedPath]

  protected implicit def pathPrefixOpt: Option[PathPrefix]

  def paths: Array[Path] = unprefixedPaths.map(_.buildPath)
}
