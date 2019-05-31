package com.example.foundkey.CH03_Searching.Seg4_HashTables;

import org.junit.Test;

import static org.junit.Assert.*;

public class FSeparateChainingHashSTTest {
    @Test
    public void usageTest() {
        FSeparateChainingHashST<Integer, String> st = new FSeparateChainingHashST<>();

        assertEquals(0, st.size());
        assertTrue(st.isEmpty());

        st.put(1, "Apple");
        st.put(3, "Banana");
        st.put(5, "Cherry");
        st.put(7, "Durain");
        st.put(9, "Egg");

        assertEquals(5, st.size());
        assertFalse(st.isEmpty());

        assertEquals("Apple", st.get(1));
        assertEquals("Banana", st.get(3));
        assertEquals("Cherry", st.get(5));
        assertEquals("Durain", st.get(7));
        assertEquals("Egg", st.get(9));

        assertTrue(st.contains(1));
        assertTrue(st.contains(3));
        assertFalse(st.contains(6));

        // delete mid
        st.delete(3);
        assertEquals(4, st.size());
        assertFalse(st.contains(3));

        // delete tail
        st.delete(9);
        assertEquals(3, st.size());
        assertFalse(st.contains(9));

        // delete head
        st.delete(1);
        assertEquals(2, st.size());
        assertFalse(st.contains(1));

        // delete all
        st.delete(5);
        st.delete(7);
        assertEquals(0, st.size());
        assertTrue(st.isEmpty());
    }
}