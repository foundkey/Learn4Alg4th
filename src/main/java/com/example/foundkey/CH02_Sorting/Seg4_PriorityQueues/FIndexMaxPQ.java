package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import java.util.Iterator;

public class FIndexMaxPQ<Key extends Comparable<Key>> extends FAbstractIndexPQ<Key> {

    public FIndexMaxPQ(int maxN) {
        super(maxN);
    }

    public Key max() {
        return root();
    }

    public int maxIndex() {
        return rootIndex();
    }

    public int delMax() {
        return delRoot();
    }

    @Override
    protected void swim(int k) {
        while (k > ROOT && less(k / 2, k)) {
            exchange(k, k / 2);
            k /= 2;
        }
    }

    @Override
    protected void sink(int k) {
        while (k * 2 <= mSize) {
            int j = 2 * k;
            // 去两子节点中较小的子节点
            if (j < mSize && less(j, j + 1)) {
                j++;
            }

            // 如果当前节点比较大的子节点大，已经是堆有序状态
            if (less(j, k)) {
                break;
            }

            exchange(k, j);
            k = j;
        }
    }

    /*
     * Iterator
     */

    @Override
    public Iterator<Integer> iterator() {
        return new IndexMaxIterator();
    }

    private class IndexMaxIterator extends IndexPQIterator {
        @Override
        protected FAbstractIndexPQ<Key> getCopy(int maxN) {
            return new FIndexMaxPQ<>(maxN);
        }
    }
}
