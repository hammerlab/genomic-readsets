organization := "org.hammerlab.genomics"
name := "readsets"
version := "1.0.6-SNAPSHOT"

addSparkDeps

deps ++= Seq(
  libs.value('adam_core).copy(revision = "0.23.2-SNAPSHOT"),
  libs.value('args4j),
  libs.value('args4s),
  "org.hammerlab" %% "spark-bam" % "1.1.0-SNAPSHOT",
  libs.value('iterators).copy(revision = "1.3.0-SNAPSHOT"),
  libs.value('htsjdk),
  libs.value('loci).copy(revision = "2.0.0-SNAPSHOT"),
  libs.value('magic_rdds).copy(revision = "1.5.0-SNAPSHOT"),
  libs.value('paths).copy(revision = "1.1.1-SNAPSHOT"),
  libs.value('slf4j),
  libs.value('spark_util).copy(revision = "1.2.0-SNAPSHOT")
)

compileAndTestDeps ++= Seq(
  libs.value('reads).copy(revision = "1.0.6-SNAPSHOT"),
  libs.value('reference)
)

// org.hammerlab.genomics:reads::tests uses org.hammerlab.genomics:utils::{compile,test}, but test-JAR deps don't
// propagate trans-deps like non-classified ones.

testDeps += libs.value('genomic_utils)
testJarTestDeps += libs.value('genomic_utils)

testUtilsVersion := "1.2.4-SNAPSHOT"
sparkTestsVersion := "2.1.0-SNAPSHOT"

publishTestJar
takeFirstLog4JProperties
