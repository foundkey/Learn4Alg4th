package com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Arrays;
import java.util.Base64;

import static org.junit.Assert.*;

public class SortBaseTest {

    @Test
    public void isSorted() {
        assertTrue(SortBase.isSorted(new Integer[]{2, 3, 5, 7, 11, 13, 17, 19}));
        assertFalse(SortBase.isSorted(new Integer[]{2, 3, 5, 7, 23, 13, 17, 19}));
    }

    private Double[] getRandomArray(int N) {
        Double[] arr = new Double[N];
        Arrays.setAll(arr, i -> StdRandom.uniform());

        return arr;
    }

    @Test
    public void less() {
        assertTrue(SortBase.less(0.1, 0.2));
        assertTrue(SortBase.less(4, 8));
        assertFalse(SortBase.less(4, 4));
        assertFalse(SortBase.less(8, 4));
    }

    @Test
    public void exchange() {
        Integer[] arr = {1, 2, 3, 4, 5};

        SortBase.exchange(arr, 0, 4);
        assertArrayEquals(new Integer[] {5, 2, 3, 4, 1},
                arr);
    }

    @Test
    public void selectionTest() {
        Double[] arr = getRandomArray(32);
        StdOut.println("Before Sort: " + Arrays.toString(arr));

        FSelection.sort(arr);
        assertTrue(SortBase.isSorted(arr));

        StdOut.println("After Sort: " + Arrays.toString(arr));
    }
}