package com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class FBinarySearchST<Key extends Comparable<Key>, Value> implements IOrderedSymbolTable<Key, Value> {

    private static final int INIT_CAPACITY = 2;
    private Key[] mKeys;
    private Value[] mValues;
    private int mSize;

    public FBinarySearchST() {
        this(INIT_CAPACITY);
    }

    public FBinarySearchST(int capacity) {
        mKeys = (Key[]) (new Comparable[capacity]);
        mValues = (Value[]) (new Object[capacity]);
        mSize = 0;
    }

    private void resize(int capacity) {
        assert capacity >= mSize;

        mKeys = Arrays.copyOf(mKeys, capacity);
        mValues = Arrays.copyOf(mValues, capacity);
    }

    @Override
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("called min() with empty symbol table");
        }
        return mKeys[0];
    }

    @Override
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("called max() with empty symbol table");
        }
        return mKeys[mSize - 1];
    }

    @Override
    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }

        int i = rank(key);
        // 有i个键小于key，那么第i + 1个键大于等于key，第i + 1个键的索引为i + 1 - 1 = i
        if (i < mSize && key.compareTo(mKeys[i]) == 0) {
            return mKeys[i];
        }

        // 没有相等键，找小于key的键中最大值
        if (i == 0) {
            // 没有小于等于key键
            return null;
        } else {
            // 有i个键小于key，那么第i个键为其中的最大键（键按升序保存），第i个键的索引为i - 1
            return mKeys[i - 1];
        }
    }

    @Override
    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }

        int i = rank(key);
        // 有i个键小于key，那么第i + 1个键大于等于key，第i + 1个键的索引为i + 1 - 1 = i
        if (i == mSize) {
            // 没有大于等于key的键
            return null;
        } else {
            return mKeys[i];
        }
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("called select() with invalid argument: " + k);
        }

        return mKeys[k];
    }

    /*
     * 返回小于key的键的数量c，
     * 由于键按照升序保存，那么第(c + 1)个键大于等于key，第(c + 1)键的索引为(c + 1 -1) = c
     * 可以推导出 key <= mKeys[rank(key)] (key存在时取等号）
     *
     * Q: 为什么不直接返回true、false？这样可以根据返回值直接判断是否查找成功，无需在比对一次
     * A: 插入新元素时，为了保证有序，需要确定插入位置。当返回索引不等于key时，返回的索引可作为新元素的插入位置。
     *    这样实现便于put()、floor()、ceiling()函数的实现
     */
    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }

        int low = 0;
        int high = mSize - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = key.compareTo(mKeys[mid]);
            if (cmp < 0) {
                high = mid - 1;
            } else if (cmp > 0) {
                low = mid + 1;
            } else {
                return mid;
            }
        }

        return low;
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        delete(min());
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        delete(max());
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

        int i = rank(key);

        // 有i个键小于key，那么第i + 1个键大于等于key，第i + 1个键的索引为i + 1 - 1 = i
        if (i < mSize && mKeys[i].compareTo(key) == 0) {
            // key is already in table
            mValues[i] = value;
        }

        // insert new key-value pair
        if (mSize == mKeys.length) {
            resize(mKeys.length * 2);
        }

        // 为保证有序，逐个向后移动，给插入目标腾位置
        for (int j = mSize; j > i; j--) {
            mKeys[j] = mKeys[j - 1];
            mValues[j] = mValues[j - 1];
        }
        mKeys[i] = key;
        mValues[i] = value;
        mSize++;

        assert check();
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

        if (isEmpty()) {
            return null;
        }

        int i = rank(key);
        // i指向的key只能保证小于等于搜索目标，故这里要再判断一次是否相等
        if (i < mSize && mKeys[i].compareTo(key) == 0) {
            return mValues[i];
        }

        return null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }

        if (isEmpty()) {
            return;
        }

        int i = rank(key);

        // key not exist
        if (i == mSize || mKeys[i].compareTo(key) != 0) {
            return;
        }

        // 将删除的目标移动到末尾
        for (int j = i; j < mSize - 1; j++) {
            mKeys[j] = mKeys[j + 1];
            mValues[j] = mValues[j + 1];
        }
        mSize--;
        mKeys[mSize] = null;
        mValues[mSize] = null;

        if (mSize > 0 && mSize == mKeys.length / 4) {
            resize(mKeys.length / 2);
        }

        assert check();
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
    public int size(Key low, Key high) {
        if (low == null) {
            throw new IllegalArgumentException("first argument to size() is null");
        }

        if (high == null) {
            throw new IllegalArgumentException("second argument to size() is null");
        }

        if (low.compareTo(high) > 0) {
            return 0;
        }

        // l = rank(low) 返回小low的键的数量，第（c + 1)个键大于等于low，其索引为（c + 1 - 1) = c = rank(low)
        // h = rank(high)同理
        if (contains(high)) {
            // 键包含high，那么索引的有效区间为[l, h]
            return rank(high) - rank(low) + 1;
        } else {
            // 键不包含high，那么索引的有效区间为[l, h)
            return rank(high) - rank(low);
        }
    }

    @Override
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    @Override
    public Iterable<Key> keys(Key low, Key high) {
        if (low == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }

        if (high == null) {
            throw new IllegalArgumentException("second argument to keys() is null");
        }

        FQueue<Key> queue = new FQueue<>();
        if (low.compareTo(high) > 0) {
            return queue;
        }


        // c = rank(low) 返回小low的键的数量，第（c + 1)个键大于等于low，其索引为（c + 1 - 1) = c = rank(low)
        // i < rank(high)同理，但rank(high) <= mSize，如果循环判断条件取等号，可能会越界访问，因此high键需要额外处理
        for (int i = rank(low); i < rank(high); i++) {
            queue.enqueue(mKeys[i]);
        }

        if (contains(high)) {
            queue.enqueue(mKeys[rank(high)]);
        }

        return queue;
    }

    /*
     * Helper functions
     */

    private boolean check() {
        return isSorted() && rankCheck();
    }

    private boolean isSorted() {
        for (int i = 1; i < size(); i++) {
            if (mKeys[i].compareTo(mKeys[i - 1]) < 0)
                return false;
        }

        return true;
    }

    private boolean rankCheck() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }

        for (int i = 0; i < size(); i++) {
            if (mKeys[i].compareTo(select(rank(mKeys[i]))) != 0) {
                return false;
            }
        }

        return true;
    }
}
