package org.hammerlab.genomics.readsets

import com.esotericsoftware.kryo.Kryo
import org.apache.spark.serializer.KryoRegistrator
import org.hammerlab.genomics.{ loci, reads }

class Registrar extends KryoRegistrator {
  override def registerClasses(kryo: Kryo): Unit = {
    new reads.Registrar().registerClasses(kryo)
    new loci.set.Registrar().registerClasses(kryo)
    kryo.register(classOf[SampleRead])
    kryo.register(classOf[Array[SampleRead]])
  }
}
