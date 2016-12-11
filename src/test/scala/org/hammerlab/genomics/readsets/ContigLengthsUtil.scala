package org.hammerlab.genomics.readsets

import org.hammerlab.genomics.reference.ContigLengths

trait ContigLengthsUtil {
  def makeContigLengths(contigs: (String, Int)*): ContigLengths =
    (for {
      (contig, length) <- contigs
    } yield
      contig -> length.toLong
    ).toMap
}
