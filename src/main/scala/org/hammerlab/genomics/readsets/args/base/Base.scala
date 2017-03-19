package org.hammerlab.genomics.readsets.args.base

import org.apache.hadoop.fs.Path
import org.hammerlab.genomics.readsets.Inputs
import org.hammerlab.genomics.readsets.io.{ Input, ReadFilters }

trait Base
  extends ReadFilters {

  def noSequenceDictionary: Boolean

  def paths: Array[Path]

  def sampleNames: Array[String]

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
