organization := "org.hammerlab.genomics"
name := "readsets"
version := "1.0.0"

libraryDependencies ++= Seq(
  libraries.value('adam_core),
  libraries.value('args4j),
  libraries.value('hadoop_bam),
  libraries.value('iterators),
  libraries.value('htsjdk),
  libraries.value('loci),
  libraries.value('magic_rdds),
  libraries.value('reads),
  libraries.value('slf4j),
  libraries.value('spark_util)
)

providedDeps ++= Seq(
  libraries.value('spark),
  libraries.value('hadoop)
)

testDeps ++= Seq(
  libraries.value('spark_tests),
  libraries.value('test_utils),
  libraries.value('reads) classifier("tests")

)
