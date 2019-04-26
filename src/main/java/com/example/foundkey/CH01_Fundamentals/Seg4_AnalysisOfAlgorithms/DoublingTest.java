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
                .parallel()
                .limit(N)
                .toArray();

        Stopwatch timer = new Stopwatch();
        int cnt = ThreeSum.count(a);
        return timer.elapsedTime();
    }

    public static void main(String[] args) {
        for (int N = 250; true; N += N) {
            StdOut.printf("%7d %5.1fs\n", N, timeTrial(N));
        }
    }
}
