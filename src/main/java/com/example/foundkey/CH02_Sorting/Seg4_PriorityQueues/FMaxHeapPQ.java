package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import java.util.Iterator;

public class FMaxHeapPQ<Key extends Comparable<Key>> extends FAbstractHeapPQ<Key> {

    public FMaxHeapPQ() {
        super();
    }

    public FMaxHeapPQ(int max) {
        super(max);
    }

    public FMaxHeapPQ(Key[] keys) {
        super(keys);
    }

    public Key max() {
        return top();
    }

    public Key deleteMax() {
        return deleteTop();
    }

    // 上浮操作
    @Override
    protected void swim(int k) {
        while (k > ROOT && less(k / 2, k)) {
            exchange(k / 2, k);
            k /= 2;
        }
    }

    // 下沉操作
    @Override
    protected void sink(int k) {
        while (2 * k <= mSize) {
            int child = 2 * k;
            if (child < mSize && less(child, child + 1)) {
                // 让child指向较大的子节点
                child++;
            }

            if (!less(k, child)) {
                break;
            }

            exchange(k, child);
            k = child;
        }
    }

    @Override
    protected boolean isHeap() {
        return isMaxHeap(1);
    }

    private boolean isMaxHeap(int k) {
        if (k > mSize) {
            return true;
        }

        int left = 2 * k;
        int right = left + 1;
        if (left <= mSize && less(k, left)) {
            return false;
        }

        if (right <= mSize && less(k, right)) {
            return false;
        }

        return isMaxHeap(left) && isMaxHeap(right);
    }

    @Override
    public Iterator<Key> iterator() {
        return new MaxHeapIterator();
    }

    private class MaxHeapIterator extends HeapIterator {
        @Override
        FAbstractHeapPQ<Key> getCopy() {
            return new FMaxHeapPQ<>(size());
        }
    }
}
