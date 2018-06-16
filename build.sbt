subgroup("genomics", "readsets")
v"1.2.1"
github.repo("genomic-readsets")

spark

dep(
                adam % "0.23.4"                    ,
               bytes % "1.2.0"                     ,
           iterators % "2.2.0"                     ,
  genomics.     loci % "2.2.0"                     ,
  genomics.    reads % "1.0.7"         +testtest   ,
  genomics.reference % "1.5.0"         +testtest   ,

  // org.hammerlab.genomics:reads::tests uses org.hammerlab.genomics:utils::{compile,test}, but test-scoped deps don't
  // transit like compile-scoped ones / like you'd expect them to.
  genomics.    utils % "1.3.2" % tests +testtest   ,
              htsjdk                               ,
               paths % "1.5.0"                     ,
               slf4j                               ,
           spark_bam % "1.2.0-M1"                  ,
          spark_util % "3.0.0"
)

publishTestJar
takeFirstLog4JProperties
