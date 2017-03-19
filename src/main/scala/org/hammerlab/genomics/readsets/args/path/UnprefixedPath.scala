package org.hammerlab.genomics.readsets.args.path

import org.apache.hadoop.fs.Path
import org.hammerlab.args4s.{ Handler, OptionHandler }
import org.kohsuke.args4j.spi.Setter
import org.kohsuke.args4j.{ CmdLineParser, OptionDef }

/**
 * Type-class for a path-string that must be joined against an optional [[PathPrefix]] to generate a [[Path]].
 */
case class UnprefixedPath(value: String) {
  def buildPath(implicit prefixOpt: Option[PathPrefix]): Path =
    prefixOpt match {
      case Some(prefix) ⇒ new Path(prefix.value, value)
      case None ⇒ new Path(value)
    }
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
    UnprefixedPath
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
    UnprefixedPath
  )
