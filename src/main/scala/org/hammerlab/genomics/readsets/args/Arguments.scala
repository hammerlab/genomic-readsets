package org.hammerlab.genomics.readsets.args

import org.apache.hadoop.fs.Path
import org.kohsuke.args4j.spi.StringArrayOptionHandler
import org.kohsuke.args4j.{ Argument, Option ⇒ Args4JOption }

/**
 * Common command-line arguments for loading in one or more sets of reads, and associating a sample-name with each.
 */
trait Arguments extends Base {

  @Argument(
    required = true,
    multiValued = true,
    usage = "Paths to sets of reads: FILE1 FILE2 FILE3",
    metaVar = "PATHS"
  )
  override var paths: Array[Path] = Array()

  @Args4JOption(
    name = "--sample-names",
    handler = classOf[StringArrayOptionHandler],
    usage = "name1 ... nameN"
  )
  override var sampleNames: Array[String] = Array()
}
