package org.hammerlab.genomics.readsets.args.path

import hammerlab.path._
import org.hammerlab.test.Suite

class PrefixTest
  extends Suite {
  test("no prefix") {
    implicit def prefix: Option[Prefix] = None
    UnprefixedPath("a/b/c").buildPath should be(Path("a/b/c"))
    UnprefixedPath("file:///a/b/c").buildPath should be(Path("file:///a/b/c"))
    UnprefixedPath("/a/b/c").buildPath should be(Path("/a/b/c"))
  }

  test("relative prefix") {
    implicit def prefix: Option[Prefix] = Some(Prefix(Path("a/b/c")))
    UnprefixedPath("d/e/f").buildPath should be(Path("a/b/c/d/e/f"))
    UnprefixedPath("file:///d/e/f").buildPath should be(Path("file:///d/e/f"))
    UnprefixedPath("/d/e/f").buildPath should be(Path("/d/e/f"))
  }

  test("absolute prefix") {
    implicit def prefix: Option[Prefix] = Some(Prefix(Path("/a/b/c")))
    UnprefixedPath("d/e/f").buildPath should be(Path("/a/b/c/d/e/f"))
    UnprefixedPath("file:///d/e/f").buildPath should be(Path("file:///d/e/f"))
    UnprefixedPath("/d/e/f").buildPath should be(Path("/d/e/f"))
  }

  test("URI prefix") {
    implicit def prefix: Option[Prefix] = Some(Prefix(Path("file:///a/b/c")))
    UnprefixedPath("d/e/f").buildPath should be(Path("file:///a/b/c/d/e/f"))
    UnprefixedPath("file:///d/e/f").buildPath should be(Path("file:///d/e/f"))
    UnprefixedPath("/d/e/f").buildPath should be(Path("/d/e/f"))
  }
}
