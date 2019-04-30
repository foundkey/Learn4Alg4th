package com.example.foundkey.CH02_Sorting.Seg2_Mergesort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FInsertion;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FSelection;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.FShell;
import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Exercise_2_2_11Test {

    private Double[] getRandomArray(int N) {
        Double[] arr = new Double[N];
        Arrays.setAll(arr, i -> StdRandom.uniform());

        return arr;
    }

    @Test
    public void mergeSortOptimized() {
        Double[] arr = getRandomArray(256);
//        StdOut.println("Before Sort: " + Arrays.toString(arr));

        Exercise_2_2_11.mergeSortOptimized(arr);
        assertTrue(SortBase.isSorted(arr));

//        StdOut.println("After Sort: " + Arrays.toString(arr));
    }

    @Test
    public void insertSort() {
        Double[] testData = getRandomArray(256);
        Double[] testDataCopy = testData.clone();

        Arrays.sort(testDataCopy);
        Exercise_2_2_11.insertSort(testData, 0, testData.length - 1);

        assertArrayEquals(testDataCopy, testData);
    }

    @Test
    public void isSorted() {
        Integer[] testData = {9, 2, 3, 4, 7 ,1, 5};
        assertTrue(Exercise_2_2_11.isSorted(testData, 1, 4));
        assertFalse(Exercise_2_2_11.isSorted(testData, 2, 5));
    }
}