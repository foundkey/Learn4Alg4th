package com.example.foundkey.CH02_Sorting;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FInsertion;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FSelection;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FShell;
import com.example.foundkey.CH02_Sorting.Seg2_Mergesort.FMergeSort;
import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class SortCompare {

    public static final String ALG_INSERTION = "Insertion";
    public static final String ALG_SELECTION = "Selection";
    public static final String ALG_SHELL = "Shell";
    public static final String ALG_INSERTION_SENTINEL = "InsertionSentinel";
    public static final String ALG_INSERTION_SENTINEL2 = "InsertionSentinel2";
    public static final String ALG_INSERTION_NO_EXCHANGE = "InsertionNoExchange";
    public static final String ALG_INSERTION_OPTIMIZED = "InsertionOptimized";
    public static final String ALG_MERGE_TopDown = "MergeTopDown";
    public static final String ALG_MERGE_BOTTOM_UP = "MergeBottomUp";
    public static final String ALG_MERGE_QUICK_MERGE = "MergeQuickMerge";

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

            case ALG_INSERTION_SENTINEL2:
                FInsertion.sortSentinel2(arr);
                break;

            case ALG_INSERTION_NO_EXCHANGE:
                FInsertion.sortNoExchange(arr);
                break;

            case ALG_INSERTION_OPTIMIZED:
                FInsertion.sortOptimized(arr);
                break;

            case ALG_MERGE_TopDown:
                FMergeSort.sortTopDown(arr);
                break;

            case ALG_MERGE_BOTTOM_UP:
                FMergeSort.sortBottomUp(arr);
                break;

            case ALG_MERGE_QUICK_MERGE:
                FMergeSort.sortQuickMerge(arr);
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
//            Arrays.sortTopDown(partition);
//            System.arraycopy(partition, 0, arr, 0, size);

            total += time(alg, arr);
        }

        return total;
    }

    public static void main(String[] args) {
        int N = 400000;
        int T = 10;

//        String[] algs = {ALG_SELECTION, ALG_INSERTION, ALG_SHELL};

//        String[] algs = {ALG_INSERTION, ALG_INSERTION_SENTINEL, ALG_INSERTION_SENTINEL2,
//                ALG_INSERTION_NO_EXCHANGE, ALG_INSERTION_OPTIMIZED};

        // 比较高级排序
//        String[] algs = {ALG_SHELL, ALG_MERGE_TopDown, ALG_MERGE_BOTTOM_UP};

        // 比较归并排序
        String[] algs = {ALG_MERGE_TopDown, ALG_MERGE_BOTTOM_UP, ALG_MERGE_QUICK_MERGE};
        double[] times = new double[algs.length];

        Arrays.setAll(times, i -> timeRandomInput(algs[i], N, T));

        StdOut.printf("For %d random Doubles\n", N);
        for (int i = 0; i < algs.length; i++) {
            StdOut.printf("%s - %.2fs\n", algs[i], times[i]);
        }
    }
}
