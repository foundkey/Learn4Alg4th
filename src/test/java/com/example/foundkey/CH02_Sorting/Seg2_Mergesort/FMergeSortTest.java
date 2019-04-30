package com.example.foundkey.CH02_Sorting.Seg2_Mergesort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FMergeSortTest {

    private Double[] getRandomArray(int N) {
        Double[] arr = new Double[N];
        Arrays.setAll(arr, i -> StdRandom.uniform());

        return arr;
    }

    @Test
    public void sortTopDown() {
        Double[] arr = getRandomArray(32);
        StdOut.println("Before Sort: " + Arrays.toString(arr));

        FMergeSort.sortTopDown(arr);
        assertTrue(SortBase.isSorted(arr));

        StdOut.println("After Sort: " + Arrays.toString(arr));
    }

    @Test
    public void sortBottomUp() {
        Double[] arr = getRandomArray(32);
        StdOut.println("Before Sort: " + Arrays.toString(arr));

        FMergeSort.sortBottomUp(arr);
        assertTrue(SortBase.isSorted(arr));

        StdOut.println("After Sort: " + Arrays.toString(arr));
    }

    @Test
    public void quickMerge() {
        Double[] arr = getRandomArray(32);
        StdOut.println("Before Sort: " + Arrays.toString(arr));

        FMergeSort.sortQuickMerge(arr);
        assertTrue(SortBase.isSorted(arr));

        StdOut.println("After Sort: " + Arrays.toString(arr));
    }
}