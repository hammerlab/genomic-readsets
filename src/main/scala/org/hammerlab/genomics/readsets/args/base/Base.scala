package org.hammerlab.genomics.readsets.args.base

import java.nio.file.Path

import org.hammerlab.genomics.readsets.Inputs
import org.hammerlab.genomics.readsets.io.{ Input, ReadFilters, Sample }

trait InputArgs {
  def paths: Array[Path]

  def sampleNames: Array[Sample.Name]

  lazy val inputs: Inputs =
    paths.indices.map(i ⇒
      Input(
        i,
        if (i < sampleNames.length)
          sampleNames(i)
        else
          paths(i).toString,
        paths(i)
      )
    )
}

trait Base
  extends ReadFilters
    with InputArgs {
  def noSequenceDictionary: Boolean
}
