package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import java.util.Iterator;

public class FMinHeapPQ<Key extends Comparable<Key>> extends FAbstractHeapPQ<Key> {

    public FMinHeapPQ() {
        super();
    }

    public FMinHeapPQ(int max) {
        super(max);
    }

    public FMinHeapPQ(Key[] keys) {
        super(keys);
    }

    @Override
    protected void swim(int k) {

    }

    @Override
    protected void sink(int k) {

    }

    @Override
    protected boolean isHeap() {
        return false;
    }

    @Override
    public Iterator<Key> iterator() {
        return null;
    }
}
