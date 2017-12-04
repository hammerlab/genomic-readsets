organization := "org.hammerlab.genomics"
name := "readsets"
v"1.2.0"

addSparkDeps

dep(
           adam % "0.23.2"                    ,
         args4j                               ,
         args4s % "1.3.0"                     ,
          bytes % "1.1.0"                     ,
      iterators % "2.0.0"                     ,
  genomic_utils % "1.3.1" % tests             ,
         htsjdk                               ,
           loci % "2.0.1"                     ,
          paths % "1.4.0"                     ,
          reads % "1.0.6" + testtest          ,
      reference % "1.4.0" + testtest          ,
          slf4j                               ,
      spark_bam % "1.1.0"   snapshot          ,
     spark_util % "2.0.1"
)

dep(
  // org.hammerlab.genomics:reads::tests uses org.hammerlab.genomics:utils::{compile,test}, but test-JAR deps don't
  // propagate trans-deps like non-classified ones.
  genomic_utils % "1.3.1" + testtest
)

publishTestJar
takeFirstLog4JProperties
