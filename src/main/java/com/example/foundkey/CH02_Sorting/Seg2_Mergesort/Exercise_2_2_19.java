package com.example.foundkey.CH02_Sorting.Seg2_Mergesort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Exercise_2_2_19 {

    public static int inversions(Comparable[] arr) {
        Comparable[] aux = new Comparable[arr.length];
        Comparable[] arrCopy = arr.clone();

        return inversions(arrCopy, aux, 0, arr.length - 1);
    }

    private static int inversions(Comparable[] arr, Comparable[] aux, int low, int high) {
        if (high <= low) {
            return 0;
        }

        int count = 0;
        int mid = low + (high - low) / 2;

        count += inversions(arr, aux, low, mid);
        count += inversions(arr, aux, mid + 1, high);
        count += merge(arr, aux, low, mid, high);

        return count;
    }

    private static int merge(Comparable[] arr, Comparable[] aux, int low, int mid, int high) {
        System.arraycopy(arr, low, aux, low, high - low + 1);

        // [low, mid], [mid + 1, high]
        int left = low;
        int right = mid + 1;
        int count = 0;

        for (int i = low; i <= high; i++) {
            if (left > mid) {
                arr[i] = aux[right++];
            } else if (right > high) {
                arr[i] = aux[left++];
            } else if (SortBase.less(aux[right], aux[left])) {
                arr[i] = aux[right++];
                count += (mid - left + 1);  // 重点，hit：归并时，左右两边的子数组已经是有序的
            } else {
                arr[i] = aux[left++];
            }
        }

        return count;
    }

    /*
    * 暴力解决，O(N^2)
     */
    public static int brute(Comparable[] arr) {
        int count = 0;
        int N = arr.length;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (SortBase.less(arr[j], arr[i])) {
                    count++;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        double pre = trialTime(50);
        for (int n = 100; true; n += n) {
            double time = trialTime(n);
            StdOut.printf("%d    %.2fs    %.2f\n", n, time, time / pre);
            pre = time;
        }
    }

    private static double trialTime(int n) {
        Integer[] testData = new Integer[n];
        Arrays.setAll(testData, i -> StdRandom.uniform(n * 2));
        Stopwatch timer = new Stopwatch();
//        brute(testData);
        inversions(testData);
        return timer.elapsedTime();
    }
}
