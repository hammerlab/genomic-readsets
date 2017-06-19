package org.hammerlab.genomics.readsets

import java.util

import com.esotericsoftware.kryo.Kryo
import htsjdk.samtools.{ SAMProgramRecord, SAMReadGroupRecord, SAMSequenceDictionary, SAMSequenceRecord }
import org.apache.spark.serializer.KryoRegistrator
import org.hammerlab.bam
import org.hammerlab.bam.header.ContigLengths
import org.hammerlab.genomics.{ loci, reads, reference }

class Registrar extends KryoRegistrator {
  override def registerClasses(kryo: Kryo): Unit = {
    new reference.Registrar().registerClasses(kryo)

    new reads.Registrar().registerClasses(kryo)

    new loci.set.Registrar().registerClasses(kryo)

    kryo.register(classOf[SampleRead])
    kryo.register(classOf[Array[SampleRead]])

    bam.kryo.Registrar.registerClasses(kryo)
  }
}
