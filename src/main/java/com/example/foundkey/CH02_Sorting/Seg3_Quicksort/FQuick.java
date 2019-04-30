package com.example.foundkey.CH02_Sorting.Seg3_Quicksort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import edu.princeton.cs.algs4.StdRandom;

public class FQuick {

    public static void sort(Comparable[] arr) {
        StdRandom.shuffle(arr);
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(Comparable[] arr, int low, int high) {
        if (high <= low) {
            return;
        }

        int p = partition(arr, low, high);  // 划分为 [low, p - 1],p, [p + 1, high] 三部分
        sort(arr, low, p - 1);
        sort(arr, p + 1, high);
    }

    private static int partition(Comparable[] arr, int low, int high) {
        int left = low;
        int right = high + 1;
        Comparable v = arr[low];

        while (true) {
            // --->
            while (SortBase.less(arr[++left], v)) {
                if (left == high) {
                    break;
                }
            }

            // <----
            while (SortBase.less(v, arr[--right])) {
                if (right == low) {
                    break;
                }
            }

            if (left >= right) {
                break;
            }

            SortBase.exchange(arr, left, right);
        }

        SortBase.exchange(arr, right, low);

        return right;
    }
}
