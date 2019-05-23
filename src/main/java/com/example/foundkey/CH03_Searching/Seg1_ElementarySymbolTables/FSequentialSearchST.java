package com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;

public class FSequentialSearchST<Key, Value> implements ISymbolTable<Key, Value> {

    private int mSize;
    private Node first;

    private class Node {
        private Key key;
        private Value value;
        private Node next;

        public Node(Key key, Value value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public FSequentialSearchST() {
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

        for (Node cur = first; cur != null; cur = cur.next) {
            if (key.equals(cur.key)) {
                cur.value = value;
                return;
            }
        }

        first = new Node(key, value, first);
        mSize++;
    }

    @Override
    public int putX(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("first argument to put() is null");
        }

        if (value == null) {
            delete(key);
            return 0;
        }

        int compareCount = 0;

        for (Node cur = first; cur != null; cur = cur.next) {
            boolean b = key.equals(cur.key);
            compareCount++;

            if (b) {
                cur.value = value;
                return compareCount;
            }
        }

        first = new Node(key, value, first);
        mSize++;
        return compareCount;
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to get() is null");
        }

        for (Node cur = first; cur != null; cur = cur.next) {
            if (key.equals(cur.key)) {
                return cur.value;
            }
        }

        return null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete() is null");
        }
        first = delete(first, key);
    }

    private Node delete(Node node, Key key) {
        if (node == null) {
            return null;
        }

        if (key.equals(node.key)) {
            mSize--;
            return node.next;
        }

        node.next = delete(node.next, key);
        return node;
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
        for (Node cur = first; cur != null; cur = cur.next) {
            queue.enqueue(cur.key);
        }

        return queue;
    }
}
