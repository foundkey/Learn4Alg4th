package com.example.foundkey.CH03_Searching.Seg4_HashTables;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.ISymbolTable;

import java.util.Arrays;
import java.util.Objects;

public class FLinearProbingHashST<Key, Value> implements ISymbolTable<Key, Value> {

    private static final int INIT_CAPACITY = 4;

    private int mSize;  // size of element in symbol table
    private int mCapacity;  // size of hash table
    private Key[] mKeys;
    private Value[] mValues;

    public FLinearProbingHashST() {
        this(INIT_CAPACITY);
    }

    public FLinearProbingHashST(int mCapacity) {
        this.mCapacity = mCapacity;
        mKeys = ((Key[]) new Object[mCapacity]);
        mValues = ((Value[]) new Object[mCapacity]);
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % mCapacity;
    }

    private void resize(int capacity) {
        FLinearProbingHashST<Key, Value> temp = new FLinearProbingHashST<Key, Value>(capacity);
        for (int i = 0; i < mKeys.length; i++) {
            if (mKeys[i] != null) {
                temp.put(mKeys[i], mValues[i]);
            }
        }

        this.mCapacity = temp.mCapacity;
        this.mKeys = temp.mKeys;
        this.mValues = temp.mValues;
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

        // 保证数组使用率在 1/8 ~ 1/2 之间
        if (mSize >= mCapacity / 2) {
            resize(mCapacity * 2);
        }

        int hash = hash(key);
        while (mKeys[hash] != null) {
            if (mKeys[hash].equals(key)) {
                // key already exist, update value
                mValues[hash] = value;
                return;
            }

            hash = (hash + 1) % mCapacity;
        }

        mKeys[hash] = key;
        mValues[hash] = value;
        mSize++;
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

        int hash = hash(key);
        while (mKeys[hash] != null) {
            if (mKeys[hash].equals(key)) {
                return mValues[hash];
            }
            hash = (hash + 1) % mCapacity;
        }

        return null;
    }

    /*
     * 删除目标元素后，后续元素要重新插入符号表中
     */
    @Override
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }

        if (!contains(key)) {
            return;
        }

        int hash = hash(key);
        // find target symbol
        while (!mKeys[hash].equals(key)) {
            hash = (hash + 1) % mCapacity;
        }

        //delete it
        mKeys[hash] = null;
        mValues[hash] = null;

        // rehash all keys in same cluster
        hash = (hash + 1) % mCapacity;
        while (mKeys[hash] != null) {
            Key rehashKey = mKeys[hash];
            Value rehashValue = mValues[hash];
            // put again
            delete(hash);
            put(rehashKey, rehashValue);
            hash = (hash + 1) % mCapacity;
        }

        mSize--;

        if (mSize > 0 && mSize <= mCapacity / 8) {
            resize(mCapacity / 2);
        }

        assert check();
    }

    private void delete(int hash) {
        mKeys[hash] = null;
        mValues[hash] = null;
        mSize--;
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
        if (isEmpty()) {
            return  queue;
        }

        for (Key key : mKeys) {
            if (key != null) {
                queue.enqueue(key);
            }
        }

        return queue;
    }

    private boolean check() {
        // check that hash table is at most 50% full
        if (mCapacity < 2 * mSize) {
            System.err.println("Hash table size = " + mCapacity + "; symbol count = " + mSize);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < mCapacity; i++) {
            if (mKeys[i] == null) continue;
            else if (get(mKeys[i]) != mValues[i]) {
                System.err.println("get[" + mKeys[i] + "] = " + get(mKeys[i]) + "; values[i] = " + mValues[i]);
                return false;
            }
        }
        return true;
    }
}
