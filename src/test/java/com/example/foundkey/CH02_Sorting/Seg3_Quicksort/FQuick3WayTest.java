package com.example.foundkey.CH02_Sorting.Seg3_Quicksort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.TestUtil;
import org.junit.Test;

import static org.junit.Assert.*;

public class FQuick3WayTest {

    @Test
    public void sort() {
        Double[] testData = TestUtil.getRandomArray(32);
        FQuick3way.sort(testData);

//        StdOut.println(Arrays.toString(testData));
        assertTrue(SortBase.isSorted(testData));
    }
}