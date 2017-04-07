package org.hammerlab.genomics

import org.apache.spark.rdd.RDD
import org.hammerlab.genomics.reads.MappedRead
import org.hammerlab.genomics.readsets.io.{ Input, Sample }
import org.hammerlab.genomics.reference.Region

/**
 * This package contains functionality related to processing multiple "sets of reads" (e.g. BAM files) in the context of
 * a single analysis.
 *
 * For example, a standard somatic caller will typically load and analyze separate "normal" and "tumor" samples, each
 * corresponding to an `RDD[MappedRead]`, but which will also share metadata, like the
 * [[org.hammerlab.genomics.reference.ContigName]] of the reference they are mapped to.
 */
package object readsets {
  type PerSample[+A] = IndexedSeq[A]

  type SampleId = Sample.Id
  type NumSamples = Sample.Id

  type SampleName = Sample.Name

  type SampleRegion = Region with HasSampleId

  type Inputs = PerSample[Input]

  implicit class SampleRead(val t: (SampleId, MappedRead))
    extends AnyVal
      with HasSampleId {
    override def sampleId: SampleId = t._1
    def read: MappedRead = t._2
  }

  object SampleRead {
    implicit def unpackSampleRead(sampleRead: SampleRead): MappedRead = sampleRead.read
  }

}
