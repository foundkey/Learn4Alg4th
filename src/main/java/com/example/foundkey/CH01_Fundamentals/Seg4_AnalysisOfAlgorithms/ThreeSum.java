package com.example.foundkey.CH01_Fundamentals.Seg4_AnalysisOfAlgorithms;

import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ThreeSum {
    public static int count(int[] a) {
        // 统计和为0的元组的数量
        int N = a.length;
        int cnt = 0;

        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        cnt++;
                    }
                }
            }
        }

        return cnt;
    }

    public static int fastCount(int[] numbers) {
        int N = numbers.length;
        int count = 0;

        Arrays.sort(numbers);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (BinarySearch.indexOf(numbers, -numbers[i] - numbers[j]) > j) {
                    count++;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {
        int MAX = 4000;
        int N = 2000;
        int[] numbers = IntStream
                .generate(() -> StdRandom.uniform(-MAX, MAX))
                .distinct()
                .limit(N)
                .toArray();

        int[] copyNumbers = numbers.clone();

        StdOut.printf("%d: %d %d", N, count(numbers), fastCount(copyNumbers));
    }
}
