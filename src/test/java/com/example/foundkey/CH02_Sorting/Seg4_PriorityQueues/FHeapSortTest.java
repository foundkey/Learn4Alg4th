package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.TestUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class FHeapSortTest {

    @Test
    public void sort() {
        Double[] testData = TestUtil.getRandomArray(128);
        FHeapSort.sort(testData);
        assertTrue(SortBase.isSorted(testData));
    }
}