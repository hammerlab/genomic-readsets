package org.hammerlab.genomics.readsets.io

import org.hammerlab.genomics.readsets.io.Sample.{ Id, Name }

trait Sample {
  def id: Id
  def name: Name
}

private case class SampleImpl(id: Id, name: Name)
  extends Sample

object Sample {
  type Id = Int
  type Name = String

  implicit def fromInput(input: Input): Sample = SampleImpl(input.id, input.name)
}
