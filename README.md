Java benchmarks with JMH

This project contains next benchmarks:
1. Benchmark for demonstration how JVM handle problems with [false sharing](https://en.wikipedia.org/wiki/False_sharing)
with @Contended annotation
2. Benchmark for demonstration how [branch prediction](https://en.wikipedia.org/wiki/Branch_predictor) behaves on example 
of operation with sorted and unsorted array - useful [discussion on stackoverflow](https://stackoverflow.com/questions/11227809/why-is-it-faster-to-process-a-sorted-array-than-an-unsorted-array)