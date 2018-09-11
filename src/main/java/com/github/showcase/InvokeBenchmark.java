package com.github.showcase;

import com.github.benhmarks.BranchPredictionBenchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class InvokeBenchmark {
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + BranchPredictionBenchmark.class.getSimpleName() + ".*")
                .build();

        new Runner(opt).run();
    }
}
