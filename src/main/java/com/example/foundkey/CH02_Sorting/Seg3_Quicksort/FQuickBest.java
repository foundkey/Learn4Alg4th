package com.example.foundkey.CH02_Sorting.Seg3_Quicksort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.CH02_Sorting.Seg2_Mergesort.Exercise_2_2_11;

/*
 * 优化点：
 *  1、小数组不再递归，采用插入排序（书中建议的值位5~15）
 *  2、移除排序入口的乱序，并采用选取中位数的方法，选择划分元素
 *  3、消除划分主循环的冗余边界检测
 */
public class FQuickBest {

    private static final int CUT_OFF = 8;

    public static void sort(Comparable[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static void sort(Comparable[] arr, int low, int high) {
        if (high - low <= CUT_OFF) {
            Exercise_2_2_11.insertSort(arr, low, high);
            return;
        }

        int p = partition(arr, low, high);
        sort(arr, low, p - 1);
        sort(arr, p + 1, high);
    }

    private static int partition(Comparable[] arr, int low, int high) {
        // 寻找中位数作为划分元素
        int n = high - low + 1;
        int m = median3(arr, low, low + n / 2, high);
        SortBase.exchange(arr, low, m);

        int left = low;
        int right = high + 1;
        Comparable v = arr[low];

        // left指向右移，找到第一个小于v的元素
        while (SortBase.less(arr[++left], v)) {
            if (left == high) {
                // v为最大元素，划分后左子数组为空
                SortBase.exchange(arr, low, high);
                return high;
            }
        }

        // right指针左移，找到第一个大于v的元素
        while (SortBase.less(v, arr[--right])) {
            if (right == low + 1) {
                // v为最小元素，划分后右子数组为空
                return low;
            }
        }

        while (left < right) {
            SortBase.exchange(arr, left, right);
            // ---->
            // 第二个循环未返回，证明[right, high]中的元素都大于v，故left指针永远不会越界，可以省去边界检测
            while (SortBase.less(arr[++left], v));  // empty body
            // <----
            // v充当right指针的哨兵位，right指针指向low时循环就会停止，故可以省去边界检测
            while (SortBase.less(v, arr[--right])); // empty body
        }

        SortBase.exchange(arr, low, right);

        // [low, right - 1] <= [right] <= [right + 1, high]
        return right;
    }

    public static int median3(Comparable[] arr, int low, int mid, int high) {
        return (SortBase.less(arr[low], arr[mid])) ?
                ((SortBase.less(arr[mid], arr[high])) ? mid : (SortBase.less(arr[low], arr[high]) ? high : low)) :
                ((SortBase.less(arr[low], arr[high])) ? low : (SortBase.less(arr[mid], arr[high])) ? high : mid);
    }
}
