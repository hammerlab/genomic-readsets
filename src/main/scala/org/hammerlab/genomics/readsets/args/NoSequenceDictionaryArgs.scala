package org.hammerlab.genomics.readsets.args

import caseapp.{ HelpMessage ⇒ M }

/** Argument for using / not using sequence dictionaries to get contigs and lengths. */
case class NoSequenceDictionaryArgs(
  @M("If set, get contigs and lengths directly from reads instead of from sequence dictionary.")
  noSequenceDictionary: Boolean = false
)

