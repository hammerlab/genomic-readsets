package org.hammerlab.genomics.readsets.args.impl

import org.hammerlab.genomics.readsets.args.base.HasReference
import org.kohsuke.args4j.{ Option ⇒ Args4jOption }

trait ReferenceArgs extends HasReference {
  @Args4jOption(
    name = "--reference",
    required = true,
    usage = "Local path to a reference FASTA file"
  )
  var referencePath: String = _

  @Args4jOption(
    name = "--partial-reference",
    usage = "Treat the reference fasta as a \"partial FASTA\", comprised of segments (possibly in the interior) of contigs."
  )
  var referenceIsPartial: Boolean = false
}
