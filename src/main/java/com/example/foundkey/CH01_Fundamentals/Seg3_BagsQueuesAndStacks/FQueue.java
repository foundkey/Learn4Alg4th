package com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FQueue<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private Node mFirst;
    private Node mLast;
    private int mSize;

    public FQueue() {
        mFirst = null;
        mLast = null;
        mSize = 0;

        assert check();
    }

    public void enqueue(Item item) {
        Node oldLast = mLast;
        mLast = new Node();
        mLast.item = item;
        mLast.next = null;
        if (isEmpty()) {
            mFirst = mLast;
        } else {
            oldLast.next = mLast;
        }

        mSize++;

        assert check();
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        Item item = mFirst.item;
        mFirst = mFirst.next;
        mSize--;

        if (isEmpty()) {
            mLast = null;
        }

        assert check();

        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }

        return mFirst.item;
    }

    public boolean isEmpty() {
        return mFirst == null;
    }

    public int size() {
        return mSize;
    }

    private boolean check() {
        if (mSize < 0) {
            return false;
        }

        if (mSize == 0) {
            if (mFirst != null || mLast != null) {
                return false;
            }
        } else if (mSize == 1) {
            if (mFirst != mLast) {
                return false;
            }

            if (mFirst == null) {
                return false;
            }

            if (mFirst.next != null) {
                return false;
            }
        } else {
            if (mFirst == mLast) {
                return false;
            }

            if (mFirst == null || mLast == null) {
                return false;
            }

            if (mFirst.next == null || mLast.next != null) {
                return false;
            }

            int numberOfNodes = 0;
            for (Item item : this) {
                numberOfNodes++;
            }
            if (numberOfNodes != mSize) {
                return false;
            }

            Node lastNode = mFirst;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }

            if (lastNode != mLast) {
                return false;
            }
        }

        return true;
    }

    private class FQueueIterator implements Iterator<Item> {
        private Node current = mFirst;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new FQueueIterator();
    }
}
