package com.example.foundkey.CH02_Sorting;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FInsertion;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FSelection;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FShell;
import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class SortCompare {

    public static final String ALG_INSERTION = "Insertion";
    public static final String ALG_SELECTION = "Selection";
    public static final String ALG_SHELL = "Shell";
    public static final String ALG_INSERTION_SENTINEL = "InsertionSentinel";

    public static double time(String alg, Double[] arr) {
        // 使用alg算法排序一个数组的时间
        Stopwatch timer = new Stopwatch();

        switch (alg) {
            case ALG_SELECTION:
                FSelection.sort(arr);
                break;

            case ALG_INSERTION:
                FInsertion.sort(arr);
                break;

            case ALG_SHELL:
                FShell.sort(arr);
                break;

            case ALG_INSERTION_SENTINEL:
                FInsertion.sortSentinel(arr);
                break;

            default:
                break;
        }

        return timer.elapsedTime();
    }

    public static double timeRandomInput(String alg, int N, int T) {
        // 统计T个长度为N的数组使用alg算法排序时间
        double total = 0.0f;

        Double[] arr = new Double[N];
        for (int t = 0; t < T; t++) {
            Arrays.setAll(arr, i -> StdRandom.uniform());

            // 构造部分有序的数组
//            int size = N / 2;
//            Double[] partition = new Double[size];
//            Arrays.setAll(partition, i -> StdRandom.uniform());
//            Arrays.sort(partition);
//            System.arraycopy(partition, 0, arr, 0, size);

            total += time(alg, arr);
        }

        return total;
    }

    public static void main(String[] args) {
        int N = 6000;
        int T = 100;

//        String[] algs = {ALG_SELECTION, ALG_INSERTION, ALG_SHELL};
        String[] algs = {ALG_INSERTION, ALG_INSERTION_SENTINEL};
        double[] times = new double[algs.length];

        Arrays.setAll(times, i -> timeRandomInput(algs[i], N, T));

        StdOut.printf("For %d random Doubles\n", N);
        for (int i = 0; i < algs.length; i++) {
            StdOut.printf("%s - %.2fs\n", algs[i], times[i]);
        }
    }
}
