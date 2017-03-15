package org.hammerlab.genomics.readsets.args

import org.apache.hadoop.fs.Path
import org.hammerlab.genomics.readsets.PerSample
import org.hammerlab.genomics.readsets.io.{ Input, ReadFilterArgs }

trait Base
  extends ReadFilterArgs
    with NoSequenceDictionaryArgs {

  def paths: Array[Path]
  def sampleNames: Array[String]

  lazy val inputs: PerSample[Input] = {
    paths.indices.map(i =>
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
}
