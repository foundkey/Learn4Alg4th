package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import edu.princeton.cs.algs4.In;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class FMaxHeapPQTest {

    private FMaxHeapPQ<Integer> getTestData(int n) {
        Integer[] arr = new Integer[n];
        Arrays.setAll(arr, i -> i);

        return new FMaxHeapPQ<Integer>(arr);
    }

    @Test
    public void max() {
        int testData = 10;
        FMaxHeapPQ<Integer> heapPQ = getTestData(testData);
        int max = heapPQ.max();
        assertEquals(testData - 1, max);
    }

    @Test
    public void deleteMax() {
        int testData = 10;
        FMaxHeapPQ<Integer> heapPQ = getTestData(testData);
        assertEquals(testData, heapPQ.size());

        int max = heapPQ.deleteMax();
        assertEquals(testData - 1, max);
        assertEquals(testData - 1, heapPQ.size());
    }

    @Test
    public void iterator() {
        int testData = 6;
        FMaxHeapPQ<Integer> heapPQ = getTestData(testData);
        List<Integer> list = new ArrayList<>();
        for (Integer i : heapPQ) {
            list.add(i);
        }

        assertArrayEquals(new Integer[]{5, 4, 3, 2, 1, 0},
                list.toArray());
    }

    @Test
    public void insert() {
        int testData = 10;
        FMaxHeapPQ<Integer> heapPQ = getTestData(testData);
        assertEquals(testData, heapPQ.size());

        heapPQ.insert(testData + 1);
        assertEquals(testData + 1, heapPQ.size());
        int max = heapPQ.max();
        assertEquals(testData + 1, max);
    }

    @Test
    public void isEmpty() {
        FMaxHeapPQ<Integer> heapPQA = new FMaxHeapPQ<>();
        assertTrue(heapPQA.isEmpty());

        heapPQA.insert(1);
        assertFalse(heapPQA.isEmpty());

        heapPQA.deleteMax();
        assertTrue(heapPQA.isEmpty());
    }

    @Test
    public void size() {
        int testData = 10;
        FMaxHeapPQ<Integer> heapPQ = getTestData(testData);
        assertEquals(testData, heapPQ.size());
    }
}