package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

public interface IPriorityQueue<Key extends  Comparable<Key>> extends Iterable<Key> {

    void insert(Key v);

    // 返回优先级最高的元素
    Key top();

    // 删除并返回优先级最高的元素
    Key deleteTop();

    boolean isEmpty();

    int size();
}
