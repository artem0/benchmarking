Java benchmarks with JMH

This project contains next benchmarks:
1. Benchmark for demonstration how JVM handle problems with [false sharing](https://en.wikipedia.org/wiki/False_sharing)
with @Contended annotation
2. Benchmark for demonstration how [branch prediction](https://en.wikipedia.org/wiki/Branch_predictor) behaves on the example 
of operation with sorted and unsorted array - useful [discussion on stackoverflow](https://stackoverflow.com/questions/11227809/why-is-it-faster-to-process-a-sorted-array-than-an-unsorted-array)

## Prerequisites

* Java 8+
* Gradle

## FalseSharingBenchmark

The `-XX:-RestrictContended` flag is significant.

The output will look like this:
 
    Benchmark                                      Mode  Cnt          Score         Error  Units
    ContendedBenchmarks.padded                    thrpt   30  236434399.252 ± 9209297.004  ops/s
    ContendedBenchmarks.padded:updatePaddedA      thrpt   30  114409851.688 ± 4803042.336  ops/s
    ContendedBenchmarks.padded:updatePaddedB      thrpt   30  122024547.564 ± 5108447.473  ops/s
    ContendedBenchmarks.unpadded                  thrpt   30   63941093.638 ± 2478065.296  ops/s
    ContendedBenchmarks.unpadded:updateUnpaddedA  thrpt   30   32050855.915 ± 1371581.512  ops/s
    ContendedBenchmarks.unpadded:updateUnpaddedB  thrpt   30   31890237.723 ± 1344691.282  ops/s
The default benchmark mode is **Throughput** and in this case **higher value is better**

## BranchPredictionBenchmark

The output will look like this:

    Benchmark                           Mode  Cnt  Score   Error  Units
    BranchPredictionBenchmark.sorted    avgt   25  4.289 ± 0.754  ns/op
    BranchPredictionBenchmark.unsorted  avgt   25  9.742 ± 0.466  ns/op
    
Benchmark mode for BranchPredictionBenchmark is **AverageTime** and in this case **lower value is better**

For more details about modes in JMH see [specification](http://hg.openjdk.java.net/code-tools/jmh/file/6cc1450c6a0f/jmh-core/src/main/java/org/openjdk/jmh/annotations/Mode.java)