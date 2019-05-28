package com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables;

public interface IOrderedSymbolTable<Key extends Comparable<Key>, Value> extends ISymbolTable<Key, Value> {

    Key min();

    Key max();

    // 返回小于等于key的最大键
    Key floor(Key key);

    // 返回大于等于key的最小键
    Key ceiling(Key key);

    // 返回排名为k的键 [ 0, size() )
    Key select(int k);

    // 返回key在键中的排名
    int rank(Key key);

    void deleteMin();

    void deleteMax();

    // [low, high]中键的数量
    int size(Key low, Key high);

    Iterable<Key> keys(Key low, Key high);
}
