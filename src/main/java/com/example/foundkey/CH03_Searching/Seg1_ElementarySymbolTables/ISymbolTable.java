package com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables;

public interface ISymbolTable<Key, Value> {

    void put(Key key, Value value);

    // 同put()，但会返回比较次数
    int putX(Key key, Value value);

    Value get(Key key);

    void delete(Key key);

    boolean contains(Key key);

    boolean isEmpty();

    int size();

    Iterable<Key> keys();
}
