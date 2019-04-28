package com.example.foundkey.CH01_Fundamentals.Seg4_AnalysisOfAlgorithms;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Random;
import java.util.stream.IntStream;

public class DoublingTest {

    public static double timeTrial(int N) {
        int MAX = 1000000;
        int[] a = IntStream
                .generate(() -> StdRandom.uniform(-MAX, MAX))
                .distinct()
                .limit(N)
                .toArray();

        Stopwatch timer = new Stopwatch();
        int cnt = ThreeSum.fastCount(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        double prev = timeTrial(125);
        for (int N = 250; true; N += N) {
            double time = timeTrial(N);
            StdOut.printf("%7d %5.1fs %5.1f\n", N, timeTrial(N), time / prev);
            prev = time;
        }
    }
}
