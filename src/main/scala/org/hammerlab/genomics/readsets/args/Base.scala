package org.hammerlab.genomics.readsets.args

import hammerlab.path._
import org.hammerlab.genomics.readsets.Inputs
import org.hammerlab.genomics.readsets.io.{ Input, ReadFilterArgs, Sample }

trait InputArgs {
  self: Base ⇒

  def paths: Array[Path]

  def sampleNames: Array[Sample.Name]

  lazy val inputs: Inputs =
    paths
      .indices
      .map {
        i ⇒
          Input(
            i,
            if (i < sampleNames.length)
              sampleNames(i)
            else
              paths(i).toString,
            paths(i)
          )
      }
}

trait Base
  extends InputArgs{
  def readFilterArgs: ReadFilterArgs
  def noSequenceDictionaryArgs: NoSequenceDictionaryArgs
  def inputs: Inputs
}
