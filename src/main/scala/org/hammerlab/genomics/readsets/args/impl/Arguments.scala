package org.hammerlab.genomics.readsets.args.impl

import org.hammerlab.genomics.readsets.args.base.PrefixedPathsBase
import org.hammerlab.genomics.readsets.io.ReadFilterArgs

/**
 * Command-line arguments for loading in one or more sets of reads, and associating a sample-name with each.
 */
trait Arguments
  extends PrefixedPathsBase
    with PathsArgs
    with SampleNamesArg
    with ReadFilterArgs
    with NoSequenceDictionaryArgs
