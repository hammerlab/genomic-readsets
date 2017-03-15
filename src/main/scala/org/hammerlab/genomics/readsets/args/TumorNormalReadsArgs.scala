package org.hammerlab.genomics.readsets.args

import org.apache.hadoop.fs.Path
import org.hammerlab.genomics.readsets.io.ReadFilterArgs
import org.kohsuke.args4j.{ Option ⇒ Args4jOption }
import org.hammerlab.args4s.PathHandler

/** Arguments for accepting two sets of reads (tumor + normal). */
trait TumorNormalReadsArgs extends Base with ReadFilterArgs {

  @Args4jOption(
    name = "--normal-reads",
    metaVar = "X",
    required = true,
    handler = classOf[PathHandler],
    usage = "Path to 'normal' aligned reads"
  )
  var normalReads: Path = _

  @Args4jOption(
    name = "--tumor-reads",
    metaVar = "X",
    required = true,
    handler = classOf[PathHandler],
    usage = "Path to 'tumor' aligned reads"
  )
  var tumorReads: Path = _

  override def paths = Array(normalReads, tumorReads)

  val normalSampleName = "normal"
  val tumorSampleName = "tumor"

  override def sampleNames = Array(normalSampleName, tumorSampleName)
}
