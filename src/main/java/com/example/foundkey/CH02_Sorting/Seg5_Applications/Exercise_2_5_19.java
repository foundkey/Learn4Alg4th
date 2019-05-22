package com.example.foundkey.CH02_Sorting.Seg5_Applications;

import edu.princeton.cs.algs4.Inversions;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

// Kendall Tau distance
public class Exercise_2_5_19 {

    // return Kendall tau distance between two permutations
    public static long distance(int[] a, int[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int n = a.length;
        int[] ainv = new int[n];    // ainv[i]: i在a中的位置，辅助数组
        for (int i = 0; i < n; i++) {
            ainv[a[i]] = i;
        }

        Integer[] bnew = new Integer[n];    // bnew[i]: b[i]在a中的位置
        Arrays.setAll(bnew, i -> ainv[b[i]]);

        // 如果a与b完全一致，那么bnew是自然数排列。如果a与b有1处不一致，那么bnew的逆序对数量加1
        return Inversions.count(bnew);
    }

    // return a random permutation of size n
    public static int[] permutation(int n) {
        int[] a = new int[n];
        Arrays.setAll(a, i -> i);
        StdRandom.shuffle(a);
        return a;
    }

    public static void main(String[] args) {
        int n = 6;
        int[] a = permutation(n);
        int[] b = permutation(n);
//        int[] a = {0, 3, 1, 6, 2, 5, 4};
//        int[] b = {1, 0, 3, 6, 4, 2, 5};

        // print initial permutation
        for (int i = 0; i < n; i++) {
            StdOut.println(a[i] + " " + b[i]);
        }
        StdOut.println();

        StdOut.println("inversions = " + distance(a, b));
    }
}
