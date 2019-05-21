package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class FAbstractIndexPQ<Key extends Comparable<Key>> implements Iterable<Integer>{
    protected static final int ROOT = 1;  // 不使用索引为0的元素，以为索引为1的元素为根
    protected int mSize;
    protected int[] mPQ; // 保存mKeys索引，对索引进行对堆有序化，mKeys[mPQ[ROOT]]为最小元素
    protected int[] mReversePQ;  // 辅助数组，mReversePQ[i]表示索引i在mPQ中的位置，mReversePQ[mPQ[i]] = mPQ[mReversePQ[i]] = i
    protected Key[] mKeys;    // 不使用mKeys[0]，mKeys[i]表示索引为i的元素

    public FAbstractIndexPQ(int maxN) {
        if (maxN < 0) {
            throw new IllegalArgumentException();
        }

        mSize = 0;
        // 索引为0的元素不使用
        mKeys = (Key[]) new Comparable[maxN + 1];
        mPQ = new int[maxN + 1];
        mReversePQ = new int[maxN + 1];
        // 没有使用的索引使用-1表示
        Arrays.fill(mReversePQ, -1);
    }

    public void insert(int index, Key key) {
        if (contains(index)) {
            throw new IllegalArgumentException("index is already in the priority queue");
        }

        mSize++;
        mReversePQ[index] = mSize;
        mPQ[mSize] = index;
        mKeys[index] = key;
        swim(mSize);
    }

    // 修改索引为index的元素
    public void changeKey(int index, Key key) {
        if (!contains(index)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }
        mKeys[index] = key;
        swim(mReversePQ[index]);
        sink(mReversePQ[index]);
    }

    public boolean contains(int index) {
        return mReversePQ[index] != -1;
    }

    public Key keyOf(int index) {
        if (!contains(index)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }

        return mKeys[index];
    }

    public void delete(int index) {
        if (!contains(index)) {
            throw new NoSuchElementException("index is not in the priority queue");
        }

        int heapIndex = mReversePQ[index];
        exchange(heapIndex, mSize--);   // 将删除目标和最后一个元素交换
        swim(heapIndex);
        sink(heapIndex);
        // 清空该索引下的元素
        mKeys[index] = null;
        mReversePQ[index] = -1;
    }

    public boolean isEmpty() {
        return mSize == 0;
    }

    public int size() {
        return mSize;
    }

    public Key root() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }

        return mKeys[mPQ[ROOT]];
    }

    public int rootIndex() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }
        return mPQ[ROOT];
    }

    // 删除根节点，并返回根节点的索引
    public int delRoot() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue underflow");
        }

        int rootIndex = mPQ[ROOT];
        exchange(ROOT, mSize--);
        sink(ROOT);

        assert mPQ[mSize + 1] == rootIndex;

        mReversePQ[rootIndex] = -1;
        mKeys[rootIndex] = null;
        mPQ[mSize + 1] = -1;    // 可以省略该步

        return rootIndex;
    }

    /*
     * Helper functions
     */
    protected void exchange(int i, int j) {
        int temp = mPQ[i];
        mPQ[i] = mPQ[j];
        mPQ[j] = temp;

        mReversePQ[mPQ[i]] = i;
        mReversePQ[mPQ[j]] = j;
    }

    protected boolean less(int i, int j) {
        // 比较索引指向的目标
        return mKeys[mPQ[i]].compareTo(mKeys[mPQ[j]]) < 0;
    }

    protected abstract void swim(int k);

    protected abstract void sink(int k);

    protected abstract class IndexPQIterator implements Iterator<Integer> {
        private FAbstractIndexPQ<Key> copy;

        public IndexPQIterator() {
            copy = getCopy(mPQ.length - 1);
            for (int i = 1; i <= mSize; i++) {
                copy.insert(mPQ[i], mKeys[mPQ[i]]);
            }
        }

        protected abstract FAbstractIndexPQ<Key> getCopy(int maxN);

        @Override
        public boolean hasNext() {
            return !copy.isEmpty();
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return copy.delRoot();
        }
    }
}
