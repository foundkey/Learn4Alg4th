package com.example.foundkey.CH02_Sorting;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FInsertion;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FSelection;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FShell;
import com.example.foundkey.CH02_Sorting.Seg2_Mergesort.Exercise_2_2_11;
import com.example.foundkey.CH02_Sorting.Seg2_Mergesort.FMergeSort;
import com.example.foundkey.CH02_Sorting.Seg3_Quicksort.FQuick;
import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public static final String ALG_MERGE_OPTIMIZED = "MergeOptimized";
    public static final String ALG_QUICK = "Quick";
    public static final String ALG_QUICK3WAY = "Quick3way";

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

            case ALG_MERGE_OPTIMIZED:
                Exercise_2_2_11.mergeSortOptimized(arr);
                break;

            case ALG_QUICK:
                FQuick.sort(arr);
                break;

            case ALG_QUICK3WAY:
                FQuick.sort(arr);
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
            // 构造随机数组
            Arrays.setAll(arr, i -> StdRandom.uniform());

            // 构造部分有序的数组
//            int size = N / 2;
//            Double[] partition = new Double[size];
//            Arrays.setAll(partition, i -> StdRandom.uniform());
//            Arrays.sortTopDown(partition);
//            System.arraycopy(partition, 0, arr, 0, size);

            // 构造带有重复元素的数组
//            arr = createTestData(N);

            total += time(alg, arr);
        }

        return total;
    }

    private static Double[] createTestData(int N) {
        int repeat = N / 5;
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            list.add(StdRandom.uniform());
        }

        while (list.size() < N) {
            int index = StdRandom.uniform(repeat);
            list.add(list.get(index));
        }

        return list.toArray(new Double[0]);
    }

    public static void main(String[] args) {
        int N = 400000;
        int T = 100;

//        String[] algs = {ALG_SELECTION, ALG_INSERTION, ALG_SHELL};

        /*
        * For 4000 random Doubles
        * Insertion - 1.27s
        * InsertionSentinel - 1.10s
        * InsertionSentinel2 - 1.14s
        * InsertionNoExchange - 0.69s *
        * InsertionOptimized - 0.88s
        *
        * 插入排序优化核心：减少交换的次数（逐个后移代替交换）
         */
//        String[] algs = {ALG_INSERTION, ALG_INSERTION_SENTINEL, ALG_INSERTION_SENTINEL2,
//                ALG_INSERTION_NO_EXCHANGE, ALG_INSERTION_OPTIMIZED};

        /*
         * 比较归并排序
         * For 400000 random Doubles
         *      MergeTopDown - 8.03s
         *      MergeBottomUp - 9.16s
         *      MergeQuickMerge - 7.63s
         *      MergeOptimized - 6.78s
         *
         * 归并排序优化核心：
         *      1、减少辅助数组的拷贝次数（将中级排序结果直接保存到辅助数组）
         *      2、分割到小数组时（长度为7左右），采用基本排序，不继续递归
         *      3、判断归并前数组是否有序（左右子数组交界处是否递增），避免不必要的归并
         */
//        String[] algs = {ALG_MERGE_TopDown, ALG_MERGE_BOTTOM_UP, ALG_MERGE_QUICK_MERGE, ALG_MERGE_OPTIMIZED};

        /*
         * 快速排序比较
         *  For 400000 random Doubles, no repeat element
         *  Quick - 9.47s
         *  Quick3way - 9.04s
         *
         *  For 400000 random Doubles, have repeat element
         *  Quick - 5.86s
         *  Quick3way - 5.72s
         * 有无重复元素，三划分的算法都比较快
         */
        String[] algs = {ALG_QUICK, ALG_QUICK3WAY};

        // 比较高级排序
//        String[] algs = {ALG_SHELL, ALG_MERGE_OPTIMIZED, ALG_QUICK};

        double[] times = new double[algs.length];

        Arrays.setAll(times, i -> timeRandomInput(algs[i], N, T));

        StdOut.printf("For %d random Doubles\n", N);
        for (int i = 0; i < algs.length; i++) {
            StdOut.printf("%s - %.2fs\n", algs[i], times[i]);
        }
    }
}
