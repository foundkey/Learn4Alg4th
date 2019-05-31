package com.example.foundkey.CH03_Searching.Seg4_HashTables;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.FSequentialSearchST;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.ISymbolTable;

import java.util.Arrays;

public class FSeparateChainingHashST<Key, Value> implements ISymbolTable<Key, Value> {

    private static final int INIT_CAPACITY = 4;

    private int mSize;
    private int mChains;
    private FSequentialSearchST<Key, Value>[] mSt;

    public FSeparateChainingHashST() {
        this(INIT_CAPACITY);
    }

    public FSeparateChainingHashST(int chains) {
        mChains = chains;
        mSt = (FSequentialSearchST<Key, Value>[]) new FSequentialSearchST[chains];
        Arrays.setAll(mSt, i -> new FSequentialSearchST<>());
    }

    private void resize(int chains) {
        FSeparateChainingHashST<Key, Value> temp = new FSeparateChainingHashST<>(chains);
        for (FSequentialSearchST<Key, Value> st : mSt) {
            for (Key key : st.keys()) {
                temp.put(key, st.get(key));
            }
        }

        this.mChains = temp.mChains;
        this.mSize = temp.mSize;
        this.mSt = temp.mSt;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % mChains;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }

        if (value == null) {
            delete(key);
            return;
        }

        // 书中证明链表评价长度大于10时，效率开始下降，这个时候需要扩容
        if (mSize >= 10 * mChains) {
            resize(2 * mChains);
        }

        int i = hash(key);
        if (!mSt[i].contains(key)) {
            mSize++;
        }
        mSt[i].put(key, value);
    }

    @Override
    public int putX(Key key, Value value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }
        return mSt[hash(key)].get(key);
    }

    @Override
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }

        int i = hash(key);
        if (mSt[i].contains(key)) {
            mSt[i].delete(key);
            mSize--;
        }

        // 链表平均长度小于2时，回收空间
        if (mChains > INIT_CAPACITY && mSize <= 2 * mChains) {
            resize(mChains / 2);
        }
    }

    @Override
    public boolean contains(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return mSize;
    }

    @Override
    public Iterable<Key> keys() {
        FQueue<Key> queue = new FQueue<>();
        for (FSequentialSearchST<Key, Value> st : mSt) {
            for (Key key : st.keys()) {
                queue.enqueue(key);
            }
        }

        return queue;
    }
}
