package com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts;

public class FShell {

    public static void sort(Comparable[] arr) {
        int N = arr.length;
        int h = 1;

        // 使用这样的递增序列，可以减少shell排序中的比较次数，提高排序效率
        while (h < N / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            // 将数组变为h有序
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && SortBase.less(arr[j], arr[j - h]); j -= h) {
                    SortBase.exchange(arr, j, j - h);
                }
            }

            h /= 3;
        }
    }
}
