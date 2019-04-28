package com.example.foundkey.CH01_Fundamentals.Seg4_AnalysisOfAlgorithms;

import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class TwoSum {
    /*
    * 暴力法统计和为0的二元组
     */
    public static int count(int[] numbers) {
        int count = 0;

        for (int i = 0; i < numbers.length; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] + numbers[j] == 0) {
                    count++;
                }
            }
        }

        return count;
    }

    /**
     * 使用二分法辅助统计和为0的二元组
     * PS：必须保证数组里的数唯一，不会统计的数量会比{@link #count(int[])}少
     */
    public static int fastCount(int[] numbers) {
        Arrays.sort(numbers);
        int count = 0;

        for (int i = 0; i < numbers.length; i++) {
            int index = BinarySearch.indexOf(numbers, -numbers[i]);
            if (index> i) {
                count++;
            }
        }

        return count;
    }

    public static void timeTrial(int N) {
        int MAX = 10000000;
        int[] numbers = IntStream
                .generate(() -> StdRandom.uniform(-MAX, MAX))
                .distinct()
                .limit(N)
                .toArray();

        int[] copyNumbers = numbers.clone();

        StdOut.printf("N = %d\n", N);
        Stopwatch stopwatch = new Stopwatch();
        int count = count(numbers);
        double useTime = stopwatch.elapsedTime();
        StdOut.printf("count: %d, time: %.4f\n", count, useTime);

        stopwatch.restart();
        int fastCount = fastCount(copyNumbers);
        double fastUusTime = stopwatch.elapsedTime();
        StdOut.printf("fastCount: %d, time: %.4f\n", fastCount, fastUusTime);
        StdOut.println();
    }

    public static void main(String[] args) {
        for (int N = 500; true; N += N) {
            timeTrial(N);
        }
//        timeTrial(20000);
    }
}
