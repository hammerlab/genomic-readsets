package org.hammerlab.genomics.readsets.kryo

import com.esotericsoftware.kryo.Kryo
import org.apache.spark.internal.io.FileCommitProtocol.TaskCommitMessage
import org.apache.spark.serializer.KryoRegistrator
import org.hammerlab.bam
import org.hammerlab.genomics.readsets.SampleRead
import org.hammerlab.genomics.{ loci, reads, reference }

class Registrar extends KryoRegistrator {
  override def registerClasses(kryo: Kryo): Unit = {
    new reference.Registrar().registerClasses(kryo)

    new reads.Registrar().registerClasses(kryo)

    new loci.set.Registrar().registerClasses(kryo)

    kryo.register(classOf[SampleRead])
    kryo.register(classOf[Array[SampleRead]])

    new bam.kryo.Registrar().registerClasses(kryo)

    // https://issues.apache.org/jira/browse/SPARK-21569
    kryo.register(classOf[TaskCommitMessage])
  }
}
