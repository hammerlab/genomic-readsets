package org.hammerlab.genomics.readsets

import com.esotericsoftware.kryo.Kryo
import org.apache.spark.serializer.KryoRegistrator
import org.hammerlab.genomics.{ loci, reads, reference }

class Registrar extends KryoRegistrator {
  override def registerClasses(kryo: Kryo): Unit = {
    // In Scala 2.10.x, ContigName and Locus are boxed and need to be kryo-registered.
    new reference.Registrar().registerClasses(kryo)

    new reads.Registrar().registerClasses(kryo)

    new loci.set.Registrar().registerClasses(kryo)

    kryo.register(classOf[SampleRead])
    kryo.register(classOf[Array[SampleRead]])
  }
}
