package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class FIndexMaxPQTest {

    @Test
    public void iterator() {
        FIndexMaxPQ<Integer> maxPQ = new FIndexMaxPQ<>(16);

        maxPQ.insert(1, 2);
        maxPQ.insert(2, 3);
        maxPQ.insert(3, 5);
        maxPQ.insert(4, 7);

        Iterator<Integer> iterator = maxPQ.iterator();
        List<Integer> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }

        assertArrayEquals(new Integer[] {4, 3, 2, 1}, list.toArray(new Integer[0]));
    }

    @Test
    public void max() {
        FIndexMaxPQ<Integer> maxPQ = new FIndexMaxPQ<>(16);

        maxPQ.insert(1, 2);
        maxPQ.insert(2, 3);
        maxPQ.insert(3, 5);
        maxPQ.insert(4, 7);

        assertEquals(Integer.valueOf(7), maxPQ.max());
        assertEquals(Integer.valueOf(7), maxPQ.max());
        assertEquals(4, maxPQ.delMax());
        assertEquals(Integer.valueOf(5), maxPQ.max());
    }
}