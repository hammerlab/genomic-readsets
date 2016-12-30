organization := "org.hammerlab.genomics"
name := "readsets"
version := "1.0.0-SNAPSHOT"

addSparkDeps

deps ++= Seq(
  libs.value('adam_core),
  libs.value('args4j),
  libs.value('hadoop_bam),
  libs.value('iterators),
  libs.value('htsjdk),
  libs.value('loci),
  libs.value('magic_rdds),
  libs.value('slf4j),
  libs.value('spark_util)
)

compileAndTestDeps ++= Seq(
  libs.value('reads),
  libs.value('reference)
)

publishTestJar
