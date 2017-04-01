package org.hammerlab.genomics.readsets.args.base

import java.nio.file.Path

trait HasReference {
  def referencePath: Path
  def referenceIsPartial: Boolean
}
