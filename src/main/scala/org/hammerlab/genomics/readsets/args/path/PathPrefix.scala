package org.hammerlab.genomics.readsets.args.path

import org.hammerlab.args4s.OptionHandler
import org.kohsuke.args4j.spi.Setter
import org.kohsuke.args4j.{ CmdLineParser, OptionDef }

/**
 * Type-class for a path-prefix that can be added by default to [[UnprefixedPath]]s to generate
 * [[org.hammerlab.paths.Path]]s.
 */
case class PathPrefix(value: String)

/**
 * Cmd-line option-handler for an optional [[PathPrefix]].
 */
class PathPrefixOptionHandler(parser: CmdLineParser,
                              option: OptionDef,
                              setter: Setter[Option[PathPrefix]])
  extends OptionHandler[PathPrefix](
    parser,
    option,
    setter,
    "PATH",
    PathPrefix
  )
