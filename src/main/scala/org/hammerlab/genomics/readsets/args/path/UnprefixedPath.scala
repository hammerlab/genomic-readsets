package org.hammerlab.genomics.readsets.args.path

import org.apache.hadoop.fs.{ Path ⇒ HPath }
import org.hammerlab.args4s.{ Handler, OptionHandler }
import org.hammerlab.paths.Path
import org.kohsuke.args4j.spi.Setter
import org.kohsuke.args4j.{ CmdLineParser, OptionDef }

/**
 * Type-class for a path-string that must be joined against an optional [[PathPrefix]] to generate a
 * [[org.hammerlab.paths.Path]].
 */
case class UnprefixedPath(value: String) {
  def buildPath(implicit prefixOpt: Option[PathPrefix]): Path =
    prefixOpt match {
      case Some(prefix) ⇒ Path(prefix.value) / value
      case None ⇒ Path(value)
    }

//  def buildHadoopPath(implicit prefixOpt: Option[PathPrefix]): HPath =
//    prefixOpt match {
//      case Some(prefix) ⇒ buildPath
//      case None ⇒ Paths.get(value)
//    }
}

object UnprefixedPath {
  implicit def buildPath(unprefixedPath: UnprefixedPath)(implicit prefixOpt: Option[PathPrefix]): Path =
    unprefixedPath.buildPath

//  implicit def buildHadoopPath(unprefixedPath: UnprefixedPath)(implicit prefixOpt: Option[PathPrefix]): HPath =
//    unprefixedPath.buildHadoopPath
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
