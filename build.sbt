organization := "org.hammerlab.genomics"
name := "readsets"
version := "1.0.6-SNAPSHOT"

addSparkDeps

deps ++= Seq(
  libs.value('adam_core),
  libs.value('args4j),
  libs.value('args4s),
  libs.value('hadoop_bam),
  libs.value('iterators),
  libs.value('htsjdk),
  libs.value('loci),
  libs.value('magic_rdds),
  libs.value('paths),
  libs.value('slf4j),
  libs.value('spark_util)
)

compileAndTestDeps ++= Seq(
  libs.value('reads),
  libs.value('reference)
)

// org.hammerlab.genomics:reads::tests uses org.hammerlab.genomics:utils::{compile,test}, but test-JAR deps don't
// propagate trans-deps like non-classified ones.

testDeps += libs.value('genomic_utils)
testJarTestDeps += libs.value('genomic_utils)

publishTestJar
takeFirstLog4JProperties
