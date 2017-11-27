package org.hammerlab.genomics.readsets.args.base

import hammerlab.path._

trait HasReference {
  def referencePath: Path
  def referenceIsPartial: Boolean
}
