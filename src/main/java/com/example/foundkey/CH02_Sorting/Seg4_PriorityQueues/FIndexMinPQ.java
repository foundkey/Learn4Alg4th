package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import java.util.Iterator;

public class FIndexMinPQ<Key extends Comparable<Key>> extends FAbstractIndexPQ<Key> {

    // 创建一个容量为maxN的索引优先队列，索引范围为[0, maxN)
    public FIndexMinPQ(int maxN) {
        super(maxN);
    }

    public Key min() {
        return root();
    }

    public int minIndex() {
        return rootIndex();
    }

    public int delMin() {
        return delRoot();
    }

    @Override
    protected void swim(int k) {
        while (k > ROOT && less(k, k / 2)) {
            exchange(k, k / 2);
            k /= 2;
        }
    }

    @Override
    protected void sink(int k) {
        while (k * 2 <= mSize) {
            int j = 2 * k;
            // 去两子节点中较小的子节点
            if (j < mSize && less(j + 1, j)) {
                j++;
            }

            // 如果当前节点比较大的子节点小，已经是堆有序状态
            if (less(k, j)) {
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
        return new IndexMinIterator();
    }

    private class IndexMinIterator extends IndexPQIterator {
        @Override
        protected FAbstractIndexPQ<Key> getCopy(int maxN) {
            return new FIndexMinPQ<>(maxN);
        }
    }
}
