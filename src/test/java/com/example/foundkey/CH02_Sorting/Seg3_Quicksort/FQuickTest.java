package com.example.foundkey.CH02_Sorting.Seg3_Quicksort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.TestUtil;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FQuickTest {

    @Test
    public void sort() {
        Double[] testData = TestUtil.getRandomArray(128);
        FQuick.sort(testData);
        assertTrue(SortBase.isSorted(testData));

        Integer[] testData2 = new Integer[]{8, 7, 3, 3, 4, 1, 5, 7, 9, 2, 3, 6};
        FQuick.sort(testData2);
        StdOut.println(Arrays.toString(testData2));
        assertTrue(SortBase.isSorted(testData2));
    }
}