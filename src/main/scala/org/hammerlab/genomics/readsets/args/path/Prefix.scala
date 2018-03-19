package org.hammerlab.genomics.readsets.args.path

import caseapp.core.argparser.{ ArgParser, SimpleArgParser }
import hammerlab.path._

/**
 * Type-class for a path-prefix that can be added by default to [[UnprefixedPath]]s to generate
 * [[hammerlab.path.Path]]s.
 */
case class Prefix(path: Path)
object Prefix {
  implicit def unwrap(prefix: Prefix): Path = prefix.path
  implicit val parser: ArgParser[Prefix] =
    SimpleArgParser.from("dir") {
      path ⇒
        Right(
          Prefix(
            Path(
              path
            )
          )
        )
    }
}
