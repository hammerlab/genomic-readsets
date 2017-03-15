package org.hammerlab.genomics.readsets.io

import org.apache.hadoop.fs.Path
import org.hammerlab.genomics.readsets.{ SampleId, SampleName }

class Input(val id: SampleId, val sampleName: SampleName, val path: Path) extends Serializable

object Input {
  def apply(id: SampleId, sampleName: SampleName, path: Path): Input = new Input(id, sampleName, path)
  def unapply(input: Input): Option[(SampleId, SampleName, Path)] = Some((input.id, input.sampleName, input.path))
}
