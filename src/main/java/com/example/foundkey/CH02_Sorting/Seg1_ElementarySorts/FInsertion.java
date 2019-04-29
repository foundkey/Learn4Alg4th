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

        /*
        * 添加哨兵位后，效率能得到小幅度的提升
         */
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

    public static void sortSentinel2(Comparable[] arr) {
        int N = arr.length;

        // 将最小值置于最左边
        // 经过测试，使用冒泡的方式效率会稍优。
        int min = 0;
        for (int i = 1; i < N; i++) {
            if (SortBase.less(arr[i], arr[min])) {
                min = i;
            }
        }
        SortBase.exchange(arr, 0, min);

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

        /*
        * 移除交换，不添加哨兵位，效率最最优
        * 比起朴素的插入，效率有很大的提升
         */
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

    public static void sortOptimized(Comparable[] arr) {
        int N = arr.length;

        /*
        * 添加哨兵位，反而会增加算法运行时间
         */
        int exchanges = 0;
        for (int i = N - 1; i > 0; i--) {
            if (SortBase.less(arr[i], arr[i - 1])) {
                SortBase.exchange(arr, i, i - 1);
                exchanges++;
            }
        }

        // 未发生任何交换，数组已经有序
        if (exchanges == 0)
            return;

        for (int i = 1; i < N; i++) {
            Comparable v = arr[i];
            int j = i;
            while (SortBase.less(v, arr[j - 1])) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = v;
        }
    }
}
