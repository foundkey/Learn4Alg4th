package com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts;

public class FSelection {

    public static void sort(Comparable arr[]) {
        int N = arr.length;

        for (int i = 0; i < N; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (SortBase.less(arr[j], arr[min])) {
                    min = j;
                }
            }

            if (min != i) {
                SortBase.exchange(arr, min, i);
            }
        }
    }
}
