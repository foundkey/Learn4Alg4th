package com.example.foundkey.CH02_Sorting.Seg2_Mergesort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;

public class FMergeSort {

    public static void merge(Comparable[] arr, Comparable[] aux, int low, int mid, int high) {
        // 归并[low, mid]和[mid + 1, high]数组
        int left = low;
        int right = mid + 1;

        /*
        * 拷贝原数组到辅助数组，辅助数组不能作为归并方法的局部变量，不然频繁的创建数组会占据大量的时间。
        * 书中开始是作为全局变量，但是这样不安全。
        * 课后练习中，优化为排序入口的局部变量，最为参数传入归并函数
         */
        System.arraycopy(arr, low, aux, low, high - low + 1);

        /*
        * 原地归并需要一个辅助数组拷贝带归并数组
        * 归并时四种情况：
        *       左子数组为空
        *       右子数组为空
        *       左子数组当前的数字小
        *       右子数组当前的数字小
         */
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
        Comparable[] aux = new Comparable[arr.length];
        sort(arr, aux, 0, arr.length - 1);
    }

    // 自顶向下与自底向上两个归并排序，效率相近
    public static void sortBottomUp(Comparable[] arr) {
        Comparable[] aux = new Comparable[arr.length];
        int n = arr.length;

        for (int step = 1; step < n; step += step) {
            for (int low = 0; low + step < n; low += step + step) {
                /*
                * mid - low + 1 = step => mid = low + step - 1
                * high - (mid + 1) + 1 = step => high = mid + step = low + step + step - 1
                * 当数组长度不是2^n时，最后一个子数组长度会不足
                 */
                merge(arr, aux, low, low + step - 1, Math.min(low + step + step - 1, n - 1));
            }
        }
    }

    private static void sort(Comparable[] arr, Comparable[] aux, int low, int high) {
        if (high <= low) {
            return;
        }

        /*
        * 和mid = (low + high) / 2相比，减法的写法可以防止大整数相加时溢出带来的错误
         */
        int mid = low + (high - low) / 2;

        sort(arr, aux, low, mid);
        sort(arr, aux,mid + 1, high);
        merge(arr, aux, low, mid, high);
    }
}
