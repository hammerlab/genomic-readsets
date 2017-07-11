organization := "org.hammerlab.genomics"
name := "readsets"
version := "1.0.6-SNAPSHOT"

addSparkDeps

deps ++= Seq(
  adam % "0.23.2-SNAPSHOT",
  args4j,
  args4s % "1.2.3",
  iterators % "1.3.0-SNAPSHOT",
  htsjdk,
  loci % "2.0.0-SNAPSHOT",
  magic_rdds % "1.5.0-SNAPSHOT",
  paths % "1.1.1-SNAPSHOT",
  slf4j,
  spark_bam % "1.1.0-SNAPSHOT",
  spark_util % "1.2.0-SNAPSHOT"
)

compileAndTestDeps ++= Seq(
  reads % "1.0.6-SNAPSHOT",
  reference % "1.3.1-SNAPSHOT"
)

testDeps += genomic_utils % "1.2.3"

// org.hammerlab.genomics:reads::tests uses org.hammerlab.genomics:utils::{compile,test}, but test-JAR deps don't
// propagate trans-deps like non-classified ones.
testTestDeps += genomic_utils % "1.2.3"

testUtilsVersion := "1.2.4-SNAPSHOT"
sparkTestsVersion := "2.1.0-SNAPSHOT"

publishTestJar
takeFirstLog4JProperties
