package org.hammerlab.genomics.readsets.args.impl

import org.hammerlab.genomics.readsets.args.base.{ HasReference, PrefixedPathsBase }
import org.hammerlab.genomics.readsets.args.path.{ UnprefixedPath, UnprefixedPathHandler }
import org.hammerlab.paths.Path
import org.kohsuke.args4j.{ Option ⇒ Args4jOption }

trait ReferenceArgs
  extends HasReference {

  self: PrefixedPathsBase ⇒

  @Args4jOption(
    name = "--reference",
    aliases = Array("-r"),
    required = true,
    handler = classOf[UnprefixedPathHandler],
    usage = "Path to a reference FASTA file"
  )
  private var _referencePath: UnprefixedPath = _
  def referencePath: Path = _referencePath

  @Args4jOption(
    name = "--partial-reference",
    usage = "Treat the reference fasta as a \"partial FASTA\", comprised of segments (possibly in the interior) of contigs."
  )
  var referenceIsPartial: Boolean = false
}
