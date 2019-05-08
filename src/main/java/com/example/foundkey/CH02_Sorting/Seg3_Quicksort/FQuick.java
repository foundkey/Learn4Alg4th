package com.example.foundkey.CH02_Sorting.Seg3_Quicksort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import edu.princeton.cs.algs4.StdRandom;

public class FQuick {

    public static void sort(Comparable[] arr) {
        // 消除对输入的以来，避免最坏情况的出现
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
                    // 防止left指针越界
                    break;
                }
            }

            // <----
            while (SortBase.less(v, arr[--right])) {
                if (right == low) {
                    /*
                     * 防止right越界，但该判断是冗余的。
                     * v永远不会比自身小，right会在指向该元素时停下，不会越界
                     */
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
