package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class FAbstractHeapPQ<Key extends Comparable<Key>> implements IPriorityQueue<Key> {

    protected static final int ROOT = 1;  // 以下标为1的元素为根结点，下标为0的元素不使用
    protected Key[] mPQ;
    protected int mSize;  // 指向二叉堆中的最后一个元素

    public FAbstractHeapPQ(int capacity) {
        mPQ = (Key[]) new Comparable[capacity + 1];
        mSize = 0;
    }

    public FAbstractHeapPQ() {
        this(1);
    }

    public FAbstractHeapPQ(Key[] keys) {
        mSize = keys.length;
        mPQ = (Key[]) new Comparable[keys.length + 1];
        System.arraycopy(keys, 0, mPQ, 1, keys.length);

        // initial heap
        for (int k = mSize / 2; k >= ROOT; k--) {
            sink(k);
        }

        assert isHeap();
    }

    private void resize(int capacity) {
        assert capacity > mSize;

        mPQ = Arrays.copyOf(mPQ, capacity);
    }

    @Override
    public void insert(Key v) {
        if (mSize == mPQ.length - 1) {
            resize(2 * mPQ.length);
        }

        mPQ[++mSize] = v;
        swim(mSize);

        assert isHeap();
    }

    @Override
    public Key root() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ underflow");
        }
        return mPQ[ROOT];
    }

    @Override
    public Key deleteRoot() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ underflow");
        }

        Key max = mPQ[ROOT];
        exchange(ROOT, mSize);
        mPQ[mSize--] = null;
        sink(ROOT);

        if (mSize > 0 && (mSize == (mPQ.length - 1) / 4)) {
            resize(mPQ.length / 2);
        }

        assert isHeap();
        return max;
    }

    @Override
    public boolean isEmpty() {
        return mSize == 0;
    }

    @Override
    public int size() {
        return mSize;
    }

    // helper functions
    protected boolean less(int i, int j) {
        return mPQ[i].compareTo(mPQ[j]) < 0;
    }

    protected boolean greater(int i, int j) {
        return mPQ[i].compareTo(mPQ[j]) > 0;
    }

    protected void exchange(int i, int j) {
        Key t = mPQ[i];
        mPQ[i] = mPQ[j];
        mPQ[j] = t;
    }

    protected abstract boolean isHeap();

    protected abstract void swim(int k);

    protected abstract void sink(int k);

    // iterator functions
    protected abstract class HeapIterator implements Iterator<Key> {
        private  FAbstractHeapPQ<Key> copy;

        public HeapIterator() {
            copy = getCopy();
            for (int i = 1; i <= mSize; i++) {
                copy.insert(mPQ[i]);
            }
        }

        abstract FAbstractHeapPQ<Key> getCopy();

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Key next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return copy.deleteRoot();
        }
    }
}
