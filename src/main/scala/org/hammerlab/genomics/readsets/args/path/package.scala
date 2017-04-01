package org.hammerlab.genomics.readsets.args

import java.nio.file.Path
import org.apache.hadoop.fs.{Path ⇒ HPath}

package object path {
  implicit def toHadoop(path: Path): HPath = new HPath(path.toUri)
}
