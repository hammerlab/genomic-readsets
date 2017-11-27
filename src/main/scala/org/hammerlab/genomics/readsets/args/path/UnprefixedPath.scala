package org.hammerlab.genomics.readsets.args.path

import hammerlab.path._
import org.hammerlab.args4s.{ Handler, OptionHandler }
import org.kohsuke.args4j.spi.Setter
import org.kohsuke.args4j.{ CmdLineParser, OptionDef }

/**
 * Type-class for a path-string that must be joined against an optional [[PathPrefix]] to generate a
 * [[hammerlab.path.Path]].
 */
case class UnprefixedPath(value: String) {
  def buildPath(implicit prefixOpt: Option[PathPrefix]): Path = {
    val path = Path(value)
    prefixOpt match {
      case Some(prefix) if !path.isAbsolute ⇒
        Path(prefix.value) / value
      case _ ⇒
        path
    }
  }
}

object UnprefixedPath {
  implicit def buildPath(unprefixedPath: UnprefixedPath)(implicit prefixOpt: Option[PathPrefix]): Path =
    unprefixedPath.buildPath
}

/**
 * Cmd-line option-handler for [[UnprefixedPath]].
 */
class UnprefixedPathHandler(parser: CmdLineParser,
                            option: OptionDef,
                            setter: Setter[UnprefixedPath])
  extends Handler[UnprefixedPath](
    parser,
    option,
    setter,
    "PATH",
    UnprefixedPath(_)
  )

/**
 * Cmd-line option-handler for an [[Option[UnprefixedPath]]].
 */
class UnprefixedPathOptionHandler(parser: CmdLineParser,
                                  option: OptionDef,
                                  setter: Setter[Option[UnprefixedPath]])
  extends OptionHandler[UnprefixedPath](
    parser,
    option,
    setter,
    "PATH",
    UnprefixedPath(_)
  )
