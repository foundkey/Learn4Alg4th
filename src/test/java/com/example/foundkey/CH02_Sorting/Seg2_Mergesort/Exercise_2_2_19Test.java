package com.example.foundkey.CH02_Sorting.Seg2_Mergesort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Exercise_2_2_19Test {

    @Test
    public void inversions() {
        Integer[] arr = new Integer[32];
        Arrays.setAll(arr, i -> StdRandom.uniform(256));

        int count = Exercise_2_2_19.brute(arr);
//        System.out.println(Arrays.toString(arr));
        assertEquals(count, Exercise_2_2_19.inversions(arr));
    }

    @Test
    public void brute() {
        assertEquals(0, Exercise_2_2_19.brute(new Integer[]{1, 2, 3, 4, 5, 6, 7}));
        assertEquals(10, Exercise_2_2_19.brute(new Integer[]{5, 4, 3, 2, 1}));
        assertEquals(1, Exercise_2_2_19.brute(new Integer[]{1, 2, 3, 4, 6, 5}));
    }
}