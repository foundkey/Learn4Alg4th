package com.example.foundkey.CH02_Sorting.Seg2_Mergesort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;

public class FMergeSort {

    private static Comparable[] aux;    // 归并用的辅助数组

    public static void merge(Comparable[] arr, int low, int mid, int high) {
        // 归并[low, mid]和[mid + 1, high]数组
        int left = low;
        int right = mid + 1;

        System.arraycopy(arr, low, aux, low, high - low + 1);

        for (int k = low; k <= high; k++) {
            if (left > mid) {
                // 左边数组消耗完
                arr[k] = aux[right++];
            } else if (right > high) {
                // 右边数组消耗完
                arr[k] = aux[left++];
            } else if (SortBase.less(aux[left], aux[right])) {
                arr[k] = aux[left++];
            } else {
                arr[k] = aux[right++];
            }
        }
    }

    public static void sortTopDown(Comparable[] arr) {
        aux = new Comparable[arr.length];
        sort(arr, 0, arr.length - 1);
    }

    // 自顶向下与自底向上两个归并排序，效率相近
    public static void sortBottomUp(Comparable[] arr) {
        aux = new Comparable[arr.length];
        int n = arr.length;

        for (int step = 1; step < n; step += step) {
            for (int low = 0; low + step < n; low += step + step) {
                /*
                * mid - low + 1 = step => mid = low + step - 1
                * high - (mid + 1) + 1 = step => high = mid + step = low + step + step - 1
                * 当数组长度不是2^n时，最后一个子数组长度会不足
                 */
                merge(arr, low, low + step - 1, Math.min(low + step + step - 1, n - 1));
            }
        }
    }

    private static void sort(Comparable[] arr, int low, int high) {
        if (high <= low) {
            return;
        }

        int mid = low + (high - low) / 2;
        sort(arr, low, mid);
        sort(arr, mid + 1, high);
        merge(arr, low, mid, high);
    }
}
