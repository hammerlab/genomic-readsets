organization := "org.hammerlab.genomics"
name := "readsets"
version := "1.0.6-SNAPSHOT"

addSparkDeps

deps ++= Seq(
  adam % "0.23.2",
  args4j,
  args4s % "1.3.0",
  bytes % "1.0.3",
  iterators % "1.4.0",
  htsjdk,
  loci % "2.0.1",
  paths % "1.3.1",
  slf4j,
  spark_bam % "1.0.0-SNAPSHOT",
  spark_util % "2.0.1"
)

compileAndTestDeps ++= Seq(
  reads % "1.0.6",
  reference % "1.4.0"
)

testDeps += genomic_utils % "1.3.1"

// org.hammerlab.genomics:reads::tests uses org.hammerlab.genomics:utils::{compile,test}, but test-JAR deps don't
// propagate trans-deps like non-classified ones.
testTestDeps += genomic_utils % "1.3.1"

publishTestJar
takeFirstLog4JProperties
