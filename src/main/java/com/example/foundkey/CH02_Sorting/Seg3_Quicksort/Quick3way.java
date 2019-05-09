package com.example.foundkey.CH02_Sorting.Seg3_Quicksort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import edu.princeton.cs.algs4.Quick;
import edu.princeton.cs.algs4.StdRandom;

public class Quick3way {

    public static void sort(Comparable[] arr) {
        StdRandom.shuffle(arr);
        sort(arr, 0, arr.length - 1);
    }

    public static void sort(Comparable[] arr, int low, int high) {
        if (low >= high) {
            return;
        }

        /*
         * 划分过程，v与arr[cur]比较，三种情况
         *      小于v，交换arr[cur]与arr[lt]，cur与lt同时加1
         *      大于v，交换arr[cur]与arr[gt]，gt减1
         *      等于v，cur加1
         * 比较过程种，[cur, gt]的范围会逐渐缩小
         */
        int lt = low;
        int gt = high;
        int cur = low + 1;
        Comparable v = arr[low];
        while (cur <= gt) { // gt始终指向未比较的值，因此cur > t时，才算划分完成
            int cmp = v.compareTo(arr[cur]);
            if (cmp > 0) {
                SortBase.exchange(arr, cur++, lt++);
            } else if (cmp < 0) {
                SortBase.exchange(arr, cur, gt--);
            } else {
                cur++;
            }
        }

        // 划分结束后，数组分为三部分:
        //      [low, lt - 1] < v
        //      [lt, gt] = v
        //      [gt + 1, high] > v
        sort(arr, low, lt - 1);
        sort(arr, gt + 1, high);
    }
}
