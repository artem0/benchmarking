package com.github.benhmarks;

import org.openjdk.jmh.annotations.*;

import java.util.Random;

import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.openjdk.jmh.annotations.Mode.AverageTime;

@State(Scope.Thread)
@OutputTimeUnit(NANOSECONDS)
@BenchmarkMode(AverageTime)
@Fork(value = 1, jvmArgsAppend = {
        "-XX:+UseSuperWord",
        "-XX:+UnlockDiagnosticVMOptions",
        "-XX:CompileCommand=print,*SIMDLoopBenchmark.increment"})
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class SIMDLoopBenchmark {

    /**
     * -XX:+UseSuperWord (enabled by default)
     * Enables the transformation of scalar operations into superword operations.
     * -XX:-UseSuperWord for disabling the transformation of scalar operations
     *
     * For showcase SIMD in action use -XX:+UseSuperWord
     * and for disabling SIMD for comparison purpose -XX:-UseSuperWord
     */

    static final int SIZE = 1024;

    @State(Scope.Thread)
    public static class Context {
        final int[] values = new int[SIZE];
        final int[] results = new int[SIZE];

        @Setup
        public void setup() {
            Random random = new Random();
            for (int i = 0; i < SIZE; i++) {
                values[i] = random.nextInt(Integer.MAX_VALUE / 32);
            }
        }
    }

    @Benchmark
    public int[] increment(Context context) {
        for (int i = 0; i < SIZE; i++) {
            context.results[i] = context.values[i] + 1;
        }
        return context.results;
    }
}
