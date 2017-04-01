package org.hammerlab.genomics.readsets.args.base

import org.hammerlab.paths.Path

trait HasReference {
  def referencePath: Path
  def referenceIsPartial: Boolean
}
