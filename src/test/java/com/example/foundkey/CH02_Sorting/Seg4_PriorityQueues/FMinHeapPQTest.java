package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FMinHeapPQTest {

    private FMinHeapPQ<Integer> getTestData(int n) {
        Integer[] arr = new Integer[n];
        Arrays.setAll(arr, i -> i);

        return new FMinHeapPQ<Integer>(arr);
    }

    @Test
    public void min() {
        int testData = 10;
        FMinHeapPQ<Integer> heapPQ = getTestData(testData);
        int min = heapPQ.min();
        assertEquals(0, min);
    }

    @Test
    public void deleteMin() {
        int testData = 10;
        FMinHeapPQ<Integer> heapPQ = getTestData(testData);
        assertEquals(testData, heapPQ.size());

        int min = heapPQ.deleteMin();
        assertEquals(0, min);
        assertEquals(testData - 1, heapPQ.size());
    }

    @Test
    public void iterator() {
        int testData = 6;
        FMinHeapPQ<Integer> heapPQ = getTestData(testData);
        List<Integer> list = new ArrayList<>();
        for (Integer i : heapPQ) {
            list.add(i);
        }

        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5},
                list.toArray());
    }

    @Test
    public void insert() {
        int testData = 10;
        FMinHeapPQ<Integer> heapPQ = getTestData(testData);
        assertEquals(testData, heapPQ.size());

        heapPQ.insert(-1);
        assertEquals(testData + 1, heapPQ.size());
        int min = heapPQ.min();
        assertEquals(-1, min);
    }

    @Test
    public void isEmpty() {
        FMinHeapPQ<Integer> heapPQA = new FMinHeapPQ<>();
        assertTrue(heapPQA.isEmpty());

        heapPQA.insert(1);
        assertFalse(heapPQA.isEmpty());

        heapPQA.deleteMin();
        assertTrue(heapPQA.isEmpty());
    }

    @Test
    public void size() {
        int testData = 10;
        FMinHeapPQ<Integer> heapPQ = getTestData(testData);
        assertEquals(testData, heapPQ.size());
    }
}