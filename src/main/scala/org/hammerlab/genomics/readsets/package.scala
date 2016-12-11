package org.hammerlab.genomics

import org.apache.spark.rdd.RDD

/**
 * This package contains functionality related to processing multiple "sets of reads" (e.g. BAM files) in the context of
 * a single analysis.
 *
 * For example, a standard somatic caller will typically load and analyze separate "normal" and "tumor" samples, each
 * corresponding to an [[RDD[MappedRead]]], but which will also share metadata, like the
 * [[org.hammerlab.genomics.reference.ContigName]] of the reference they are mapped to.
 */
package object readsets {
  type PerSample[+A] = IndexedSeq[A]

  type SampleId = Int
  type NumSamples = Int

  type SampleName = String
}
