package org.hammerlab.genomics.readsets.args

import caseapp.{ HelpMessage ⇒ M }
import org.hammerlab.genomics.readsets.args.path.UnprefixedPath

case class ReferenceArgs(
  @M("Path to a reference FASTA file")
  referencePath: UnprefixedPath,

  @M("Treat the reference fasta as a \"partial FASTA\", comprised of segments (possibly in the interior) of contigs.")
  partialReference: Boolean = false
)
