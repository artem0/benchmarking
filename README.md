### Benchmarks with JMH

This project contains the next benchmarks:
1. Demonstration how JVM handle problems with [false sharing](https://en.wikipedia.org/wiki/False_sharing)
with @Contended annotation
2. [Branch prediction](https://en.wikipedia.org/wiki/Branch_predictor) on example of processing sorted vs unsorted array
3. SIMD showcase: loop with incrementation operators

### Prerequisites

* Java 8+
* Gradle

### Branch prediction

Benchmarks for branch predictions demonstrates phenomena when sorted arrays are processed faster than an unsorted array,
check this wonderful [discussion on stackoverflow](https://stackoverflow.com/questions/11227809/why-is-it-faster-to-process-a-sorted-array-than-an-unsorted-array)
for more details. 

`@OperationsPerInvocation` set an abstract unit of work for benchmark and allow JMH to adjust the scores appropriately.

The benchmark generates the next output for me:

    Benchmark                           Mode  Cnt  Score   Error  Units
    BranchPredictionBenchmark.sorted    avgt   25  4.289 ± 0.754  ns/op
    BranchPredictionBenchmark.unsorted  avgt   25  9.742 ± 0.466  ns/op
    
**AverageTime** mode has been used there, it means that **lower value is better**

More details about modes in JMH see 
[specification](http://hg.openjdk.java.net/code-tools/jmh/file/6cc1450c6a0f/jmh-core/src/main/java/org/openjdk/jmh/annotations/Mode.java)


### False sharing benchmark

The `-XX:-RestrictContended` flag is significant for usage ` @Contended` annotation.
[JOL](http://openjdk.java.net/projects/code-tools/jol/) can be very helpful for analysis of layout schema in JVM and 
understanding how JVM allocates memory for objects.

The benchmark compares increment operation for padded class according to preventing false sharing with filling extra 
space in cache line and unpadded - without filling extra space:
 
The output for both cases:
 
    Benchmark                                      Mode  Cnt          Score         Error  Units
    ContendedBenchmarks.padded                    thrpt   30  236434399.252 ± 9209297.004  ops/s
    ContendedBenchmarks.padded:updatePaddedA      thrpt   30  114409851.688 ± 4803042.336  ops/s
    ContendedBenchmarks.padded:updatePaddedB      thrpt   30  122024547.564 ± 5108447.473  ops/s
    ContendedBenchmarks.unpadded                  thrpt   30   63941093.638 ± 2478065.296  ops/s
    ContendedBenchmarks.unpadded:updateUnpaddedA  thrpt   30   32050855.915 ± 1371581.512  ops/s
    ContendedBenchmarks.unpadded:updateUnpaddedB  thrpt   30   31890237.723 ± 1344691.282  ops/s

**Throughput** mode has been used there, it means that **higher value is better**

Check more details about false sharing in Java in my [article](https://medium.com/@rukavitsya/what-is-false-sharing-and-how-jvm-prevents-it-82a4ed27da84).

### SIMD benchmark
[SIMD](https://en.wikipedia.org/wiki/SIMD) is a class of parallel computers in Flynn's taxonomy.
It describes computers with multiple processing elements that perform the same operation on multiple data points simultaneously.

This benchmark compares incrementation of values in an array with and without SIMD.

JVM use flag `-XX:+UseSuperWord` for transformation of scalar operations into superword operations.
This option is enabled by default. 
For benchmark which uses SIMD use should specify `-XX:+UseSuperWord` in `jvmArgsAppend` via `@Fork` annotation 
or leave blank, for disabling SIMD for comparison purpose use flag `-XX:+UseSuperWord`.
Only the Java HotSpot Server VM supports this option.

SIMD incrementation benchmark:

With SIMD:

    Iteration   1: 258.638 ns/op
    Iteration   2: 257.273 ns/op
    Iteration   3: 260.226 ns/op
    Iteration   4: 268.770 ns/op
    Iteration   5: 255.863 ns/op
    Iteration   6: 256.047 ns/op
    Iteration   7: 259.685 ns/op
    Iteration   8: 261.838 ns/op
    Iteration   9: 273.019 ns/op
    Iteration  10: 265.190 ns/op
    
    Result "increment":
      261.655 ±(99.9%) 8.604 ns/op [Average]
      (min, avg, max) = (255.863, 261.655, 273.019), stdev = 5.691
      CI (99.9%): [253.050, 270.259] (assumes normal distribution)

Without SIMD:
 
    Iteration   1: 981.102 ns/op
    Iteration   2: 1024.397 ns/op
    Iteration   3: 1010.879 ns/op
    Iteration   4: 1025.998 ns/op
    Iteration   5: 980.072 ns/op
    Iteration   6: 1003.025 ns/op
    Iteration   7: 998.950 ns/op
    Iteration   8: 1026.248 ns/op
    Iteration   9: 991.682 ns/op
    Iteration  10: 972.846 ns/op
    
    Result "increment":
      1001.520 ±(99.9%) 30.348 ns/op [Average]
      (min, avg, max) = (972.846, 1001.520, 1026.248), stdev = 20.073
      CI (99.9%): [971.172, 1031.868] (assumes normal distribution)

**AverageTime** mode has been used there, it means that **lower value is better**

### Running

Launching main method with benchmark and Gradle (class is specified via `main` parameter in `task runMain`):

`gradle runMain`

You can make `*.jar` file via `gradle jar` command, main class is specified via `Main-Class` parameter.

After generating `*.jar` file, you cal launch app with next command:

`java -jar build/libs/bencmarking-1.0.jar`

### Support in Intellij Idea

For launching benchmarks in Intellij Idea append support of annotation processor compilation:

For using with Intelij Idea follow the next steps: `Settings - > Compiler -> Annotation Processor -> Enable annotation processing`, 
check `Processor Path` and put the path of the exported `.jar` file.

### License: GNU General Public License v3.0