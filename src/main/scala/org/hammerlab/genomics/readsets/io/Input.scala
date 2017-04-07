package org.hammerlab.genomics.readsets.io

import org.hammerlab.genomics.readsets.io.Sample.{ Id, Name }
import org.hammerlab.paths.Path

trait Input
  extends Sample {
  def path: Path
}

private case class InputImpl(id: Id,
                             name: Name,
                             path: Path)
  extends Input

object Input {
  def apply(id: Id,
            name: Name,
            path: Path): Input =
    InputImpl(id, name, path)

  def unapply(input: Input): Option[(Id, Name, Path)] =
    Some(
      (
        input.id,
        input.name,
        input.path
      )
    )
}
