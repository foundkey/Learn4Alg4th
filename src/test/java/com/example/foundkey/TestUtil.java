package com.example.foundkey;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class TestUtil {

    public static Double[] getRandomArray(int N) {
        Double[] arr = new Double[N];
        Arrays.setAll(arr, i -> StdRandom.uniform());

        return arr;
    }
}
