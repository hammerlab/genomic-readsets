package org.hammerlab.genomics.readsets

import org.apache.spark.SparkContext
import org.hammerlab.genomics.loci.parsing.ParsedLoci
import org.hammerlab.genomics.loci.set.LociSet
import org.hammerlab.genomics.reads.ReadsUtil
import org.hammerlab.genomics.readsets.io.TestInputConfig
import org.hammerlab.genomics.reference.test.ContigLengthsUtil
import org.hammerlab.spark.test.suite.SparkSuite

trait ReadSetsUtil
  extends ContigLengthsUtil
    with ReadsUtil {

  self: SparkSuite ⇒

  def makeReadSets(inputs: Inputs, loci: ParsedLoci): (ReadSets, LociSet) = {
    val readsets = ReadSets(sc, inputs, config = TestInputConfig(loci))
    (readsets, LociSet(loci, readsets.contigLengths))
  }
}
