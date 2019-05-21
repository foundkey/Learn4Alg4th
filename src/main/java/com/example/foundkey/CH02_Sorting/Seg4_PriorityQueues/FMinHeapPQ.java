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

    public Key min() {
        return root();
    }

    public Key deleteMin() {
        return deleteRoot();
    }

    @Override
    protected void swim(int k) {
        // 如果k不是根节点，且比父节点小，上浮
        while (k > ROOT && greater(k / 2, k)) {
            exchange(k, k / 2);
            k /= 2;
        }
    }

    @Override
    protected void sink(int k) {
        while (k * 2 <= mSize) {
            int child = k * 2;

            // 使child指向较小是子节点
            if (child < mSize && greater(child, child + 1)) {
                child++;
            }

            if (!greater(k, child)) {
                break;
            }

            exchange(k, child);
            k = child;
        }
    }

    @Override
    protected boolean isHeap() {
        return isMinHeap(ROOT);
    }

    private boolean isMinHeap(int k) {
        if (k > mSize) {
            return true;
        }

        int left = k * 2;
        int right = left + 1;
        if (left <= mSize && greater(k, left)) {
            return false;
        }

        if (right <= mSize && greater(k, right)) {
            return false;
        }

        return isMinHeap(left) && isMinHeap(right);
    }

    @Override
    public Iterator<Key> iterator() {
        return new MinHeapIterator();
    }

    private class MinHeapIterator extends HeapIterator {
        @Override
        FAbstractHeapPQ<Key> getCopy() {
            return new FMinHeapPQ<>(size());
        }
    }
}
