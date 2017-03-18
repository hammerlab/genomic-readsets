package org.hammerlab.genomics.readsets.args.base

trait HasReference {
  def referencePath: String
  def referenceIsPartial: Boolean
}
