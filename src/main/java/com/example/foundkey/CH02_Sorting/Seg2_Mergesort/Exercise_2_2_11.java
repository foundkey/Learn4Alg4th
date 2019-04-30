package com.example.foundkey.CH02_Sorting.Seg2_Mergesort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Exercise_2_2_11 {

    public static int CUTOFF = 7;

    public static void mergeSortOptimized(Comparable[] arr) {
        Comparable[] aux = arr.clone();
        mergeSortOptimized(arr, aux, 0, arr.length - 1);
    }

    /*
    * 借助src数组，将排序结果保存到dst数组
     */
    private static void mergeSortOptimized(Comparable[] dst, Comparable[] src, int low, int high) {
        if (high <= low + CUTOFF) {
            insertSort(dst, low, high);
            return;
        }

        int mid = low + (high - low) / 2;
        // 直接将排序结果保存到辅助数组，归并的时候可以免去再复制数据到辅助数组
        mergeSortOptimized(src, dst, low, mid);
        mergeSortOptimized(src, dst, mid + 1, high);

        // 待归并的两个子数组都是递增的，如果交界的处的两个数组依然是递增关系，那两个子数组拼接后是有序的，无需归并
        if (SortBase.less(src[mid], src[mid + 1])) {
            System.arraycopy(src, low, dst, low, high - low + 1);
            return;
        }

        merge(dst, src, low, mid, high);
    }

    private static void merge(Comparable[] dst, Comparable[] src, int low, int mid, int high) {
        // 归并时，src数组中已经保存了排序数组的拷贝
        assert isSorted(src, low, mid);
        assert isSorted(src, mid + 1, high);

        int left = low;
        int right = mid + 1;

        for (int i = low; i <= high; i++) {
            if (left > mid) {
                dst[i] = src[right++];
            } else if (right > high) {
                dst[i] = src[left++];
            } else if (SortBase.less(src[left], src[right])) {
                dst[i] = src[left++];
            } else {
                dst[i] = src[right++];
            }
        }

        assert isSorted(dst, low, high);
    }

    public static void insertSort(Comparable[] dst, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            int j = i;
            Comparable t = dst[j];
            while (j > low && SortBase.less(t, dst[j - 1])) {
                dst[j] = dst[j - 1];
                j--;
            }
            dst[j] = t;
        }
    }

    public static boolean isSorted(Comparable[] arr, int low, int high) {
        for (int i = low + 1; i <= high; i++) {
            if (SortBase.less(arr[i], arr[i - 1])) {
                return false;
            }
        }

        return true;
    }

    //// test Cutoff length
    public static double time(Double[] arr) {
        // 使用alg算法排序一个数组的时间
        Stopwatch timer = new Stopwatch();
        mergeSortOptimized(arr);
        return timer.elapsedTime();
    }

    public static double timeRandomInput(int N, int T) {
        // 统计T个长度为N的数组使用alg算法排序时间
        double total = 0.0f;

        Double[] arr = new Double[N];
        for (int t = 0; t < T; t++) {
            Arrays.setAll(arr, i -> StdRandom.uniform());
            total += time(arr);
        }

        return total;
    }

    // 测试Cutoff长度对效率的影响
    public static void main(String[] args) {
        int N = 200000;
        int T = 100;

        StdOut.printf("N:%d, T:%d\n", N, T);
        for (int i = 0; i < 32; i += 2) {
            double time = timeRandomInput(N, T);
            StdOut.printf("Cutoff=%d, time: %.2fs\n", i, time);
        }
    }

}
