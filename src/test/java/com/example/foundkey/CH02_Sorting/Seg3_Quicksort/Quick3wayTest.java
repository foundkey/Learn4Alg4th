package com.example.foundkey.CH02_Sorting.Seg3_Quicksort;

import com.example.foundkey.CH02_Sorting.Seg1_ElementarySorts.SortBase;
import com.example.foundkey.TestUtil;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Quick3wayTest {

    @Test
    public void sort() {
        Double[] testData = TestUtil.getRandomArray(32);
        Quick3way.sort(testData);

//        StdOut.println(Arrays.toString(testData));
        assertTrue(SortBase.isSorted(testData));
    }
}