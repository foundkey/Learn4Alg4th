package com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables;

public interface IOrderedSymbolTable<Key extends Comparable<Key>, Value> extends ISymbolTable<Key, Value> {

    Key min();

    Key max();

    Key floor(Key key);

    Key ceiling(Key key);

    int rank(Key key);

    Key select(int k);

    void deleteMin();

    void deleteMax();

    int size(Key low, Key high);

    Iterable<Key> keys(Key low, Key high);
}
