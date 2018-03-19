package org.hammerlab.genomics.readsets.args.path

import hammerlab.path._

/**
 * Type-class for a path-string that must be joined against an optional [[Prefix]] to generate a
 * [[hammerlab.path.Path]].
 */
case class UnprefixedPath(value: String) {
  def buildPath(implicit prefix: Option[Prefix]): Path = {
    val path = Path(value)
    prefix match {
      case Some(prefix) if !path.isAbsolute ⇒
        prefix / value
      case _ ⇒
        path
    }
  }
}

object UnprefixedPath {
  implicit def buildPath(unprefixedPath: UnprefixedPath)(implicit prefixOpt: Option[Prefix]): Path =
    unprefixedPath.buildPath
}
