package org.hammerlab.genomics.readsets.args.impl

import org.hammerlab.args4s.StringsOptionHandler
import org.hammerlab.genomics.readsets.args.base.Base
import org.kohsuke.args4j

trait SampleNamesArg {
  self: Base ⇒
  @args4j.Option(
    name = "--sample-names",
    handler = classOf[StringsOptionHandler],
    usage = "name1,…,nameN"
  )
  var sampleNames: Array[String] = Array()
}
