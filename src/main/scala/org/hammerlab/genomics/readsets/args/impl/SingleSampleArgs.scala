package org.hammerlab.genomics.readsets.args.impl

import org.apache.hadoop.fs.Path
import org.hammerlab.args4s.PathHandler
import org.hammerlab.genomics.readsets.args.base.Base
import org.hammerlab.genomics.readsets.io.ReadFilterArgs
import org.kohsuke.args4j.{ Option ⇒ Args4jOption }

/** Argument for accepting a single set of reads (for non-somatic variant calling). */
trait SingleSampleArgs
  extends Base
    with NoSequenceDictionaryArgs
    with ReadFilterArgs {

  @Args4jOption(
    name = "--reads",
    metaVar = "X",
    required = true,
    handler = classOf[PathHandler],
    usage = "Path to aligned reads"
  )
  var reads: Path = _

  override def paths = Array(reads)

  def sampleName = "reads"

  override def sampleNames = Array(sampleName)
}

