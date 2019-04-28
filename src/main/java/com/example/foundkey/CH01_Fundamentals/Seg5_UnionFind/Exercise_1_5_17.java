package com.example.foundkey.CH01_Fundamentals.Seg5_UnionFind;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;

public class Exercise_1_5_17 {

    public static int count(int N) {
        IUnionFind uf = new FWeightedQuickUnion();
        uf.initial(N);

        int edges = 0;
        while (uf.count() > 1) {
            int p = StdRandom.uniform(N);
            int q = StdRandom.uniform(N);
            uf.union(p, q);
            edges++;
        }

        return edges;
    }

    public static void main(String[] args) {
        int trials = 1000;     // number of trials

        StdOut.println("1/2 n ln n\t\tmean\t\tstddev");
        for (int n = 100; true; n += n) {
            testTrail(n, trials);
        }
    }

    private static void testTrail(int n, int trials) {
        int[] edges = new int[trials];              // record statistics

        // repeat the experiment trials times
        for (int t = 0; t < trials; t++) {
            edges[t] = count(n);
        }

        // report statistics
        StdOut.printf("%.6f\t\t%.3f\t\t%.6f\n", 0.5 * n * Math.log(n), StdStats.mean(edges), StdStats.stddev(edges));
    }
}
