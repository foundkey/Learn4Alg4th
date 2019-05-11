package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class FHeapSort {

    public static void sort(Comparable[] arr) {
        int n = arr.length;
        /*
         * 构造堆
         *  k = n / 2：从最后一个带有子节点的节点开始下沉操作
         */
        for (int k = n / 2; k >= 1; k--) {
            sink(arr, k, n);
//            showArr(arr, String.format("sink %d", k));
        }

//        showArr(arr, "After Create Heap: ");

        // 堆排序，递增使用大根堆
        while (n > 1) {
            exchange(arr, 1, n--);  // 根节点为最大元素，与末尾元素交换。交换后最大移除到堆外
            sink(arr, 1, n);    // 恢复堆有序
        }
    }

    public static void sortWithNoExchangeSink(Comparable[] arr) {
        int n = arr.length;
        for (int k = n / 2; k >= 1; k--) {
            sink(arr, k, n);
        }

        while (n > 1) {
            exchange(arr, 1, n--);
            noExchangeSink(arr, 1, n);
        }
    }

    private static void showArr(Comparable[] arr, String info) {
        StdOut.println(info);
        StdOut.println(Arrays.toString(arr));
    }

    // helper functions

    private static void sink(Comparable[] pq, int k, int n) {
        while (k * 2 <= n) {
            int child = k * 2;
            if (child < n && less(pq, child, child + 1)) {
                child++;
            }

            if (!less(pq, k, child)) {
                break;
            }

            exchange(pq, k, child);
            k = child;
        }
    }

    // exercise 2.4.26
    private static void noExchangeSink(Comparable[] pq, int k, int n) {
        Comparable v = pq[k];
        while (k * 2 <= n) {
            int child = k * 2;
            if (child < n && less(pq, child, child + 1)) {
                child++;
            }

            if (!less(pq, k, child)) {
                break;
            }

            pq[k - 1] = pq[child - 1];
            k = child;
        }
        pq[k - 1] = v;
    }

    /*
     * 所有操作都通过less()与exchange()方法访问数组。
     * 这两个函数内部都对索引进行-1处理，这样处理后，堆的根结点变为索引为0的元素
     */
    private static boolean less(Comparable[] pq, int i, int j) {
        return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }

    private static void exchange(Comparable[] pq, int i, int j) {
        Comparable v = pq[i - 1];
        pq[i - 1] = pq[j - 1];
        pq[j - 1] = v;
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[10];
        Arrays.setAll(arr, i -> i);

        StdRandom.shuffle(arr);
//        showArr(arr, "Test arr:");
        sort(arr);
        showArr(arr, "After sort:");
    }
}
