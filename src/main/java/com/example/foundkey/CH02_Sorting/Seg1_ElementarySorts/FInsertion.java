package com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts;

public class FInsertion {

    public static void sort(Comparable[] arr) {
        int N = arr.length;

        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && SortBase.less(arr[j], arr[j - 1]); j--) {
                SortBase.exchange(arr, j, j - 1);
            }
        }
    }

    // Exercise 2.1.24
    public static void sortSentinel(Comparable[] arr) {
        int N = arr.length;

        // 将最小值置于最左边
        for (int i = arr.length - 1; i > 0; i--) {
            if (SortBase.less(arr[i], arr[i - 1])) {
                SortBase.exchange(arr, i, i - 1);
            }
        }

        for (int i = 1; i < N; i++) {
            // 左边为最小值，保证j不会越界
            for (int j = i; SortBase.less(arr[j], arr[j - 1]); j--) {
                SortBase.exchange(arr, j, j - 1);
            }
        }
    }

    // Exercise 2.1.25
    public static void sortNoExchange(Comparable[] arr) {
        int N = arr.length;

        for (int i = 1; i < N; i++) {
            Comparable t = arr[i];
            int j = i;
            while (j > 0 && SortBase.less(t, arr[j - 1])) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = t;
        }
    }
}
