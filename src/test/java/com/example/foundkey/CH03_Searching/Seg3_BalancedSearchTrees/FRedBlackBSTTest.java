package com.example.foundkey.CH03_Searching.Seg3_BalancedSearchTrees;

import edu.princeton.cs.algs4.RedBlackBST;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FRedBlackBSTTest {
    @Test
    public void usageTest() {
        FRedBlackBST<Integer, String> st = new FRedBlackBST<>();
//        RedBlackBST<Integer, String> st = new RedBlackBST<>();

        assertEquals(0, st.size());
        assertTrue(st.isEmpty());

        st.put(1, "Apple");
        st.put(3, "Banana");
        st.put(5, "Cherry");
        st.put(7, "Durain");
        st.put(9, "Egg");

        assertEquals(5, st.size());
        assertEquals(3, st.size(4, 10));
        assertEquals(4, st.size(3, 9));
        assertFalse(st.isEmpty());

        assertEquals("Apple", st.get(1));
        assertEquals("Banana", st.get(3));
        assertEquals("Cherry", st.get(5));
        assertEquals("Durain", st.get(7));
        assertEquals("Egg", st.get(9));
        assertEquals(Integer.valueOf(5), st.select(st.rank(5)));

        assertEquals(Integer.valueOf(1), st.min());
        assertEquals(Integer.valueOf(9), st.max());

        assertTrue(st.contains(1));
        assertTrue(st.contains(3));
        assertFalse(st.contains(6));

        assertEquals(Integer.valueOf(5), st.floor(6));
        assertEquals(Integer.valueOf(7), st.ceiling(6));

        // delete mid
        st.delete(3);
        assertEquals(4, st.size());
        assertFalse(st.contains(3));

        // delete tail
        st.delete(9);
        assertEquals(3, st.size());
        assertFalse(st.contains(9));
        assertEquals(Integer.valueOf(7), st.max());

        // delete head
        st.delete(1);
        assertEquals(2, st.size());
        assertFalse(st.contains(1));
        assertEquals(Integer.valueOf(5), st.min());

        st.put(0, "Fig");
        st.deleteMin();
        assertEquals(Integer.valueOf(5), st.min());

        st.put(10, "Grape");
        st.deleteMax();
        assertEquals(Integer.valueOf(7), st.max());

        // delete all
        st.delete(5);
        st.delete(7);
        assertEquals(0, st.size());
        assertTrue(st.isEmpty());
    }

    @Test
    public void keysTest() {
        Integer[] keys = {1, 2, 3, 4, 5};
        String[] values = {"A", "B", "C", "D", "F"};

        FRedBlackBST<Integer, String> st = new FRedBlackBST<>();
        for (int i = 0; i < keys.length; i++) {
            st.put(keys[i], values[i]);
        }

        Iterable<Integer> itr = st.keys();
        List<Integer> temp = new ArrayList<>();
        for (Integer i : itr) {
            temp.add(i);
        }

        assertArrayEquals(keys, temp.toArray(new Integer[0]));
    }
}