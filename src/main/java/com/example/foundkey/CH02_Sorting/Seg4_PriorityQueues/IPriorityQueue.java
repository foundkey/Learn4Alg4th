package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

public interface IPriorityQueue<Key extends  Comparable<Key>> extends Iterable<Key> {

    void insert(Key v);

    // 返回优先级最高的元素
    Key root();

    // 删除并返回优先级最高的元素
    Key deleteRoot();

    boolean isEmpty();

    int size();
}
