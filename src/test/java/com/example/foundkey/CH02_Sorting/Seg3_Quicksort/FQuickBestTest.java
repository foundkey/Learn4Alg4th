package com.example.foundkey.CH02_Sorting.Seg3_Quicksort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.TestUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class FQuickBestTest {

    @Test
    public void sort() {
        Double[] testData = TestUtil.getRandomArray(128);
        FQuickBest.sort(testData);
        assertTrue(SortBase.isSorted(testData));
    }

    @Test
    public void median3() {
        Integer[] test = {1, 2, 3};
        assertEquals(1, FQuickBest.median3(test, 0, 1, 2));

        Integer[] test2 = {1, 3, 2};
        assertEquals(2, FQuickBest.median3(test2, 0, 1, 2));

        Integer[] test3 = {2, 3, 1};
        assertEquals(0, FQuickBest.median3(test3, 0, 1, 2));
    }
}