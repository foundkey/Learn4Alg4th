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
}
