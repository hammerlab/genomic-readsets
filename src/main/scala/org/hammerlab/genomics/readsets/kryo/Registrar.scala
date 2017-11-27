package org.hammerlab.genomics.readsets.kryo

import org.apache.spark.internal.io.FileCommitProtocol.TaskCommitMessage
import org.hammerlab.bam
import org.hammerlab.genomics.readsets.SampleRead
import org.hammerlab.genomics.{ loci, reads, reference }
import org.hammerlab.kryo._

class Registrar extends spark.Registrar(
  new reference.Registrar(),

  new reads.Registrar(),

  new loci.set.Registrar(),

  arr[SampleRead],

  bam.spark.load.Registrar(),

  // https://issues.apache.org/jira/browse/SPARK-21569
  cls[TaskCommitMessage]
)
