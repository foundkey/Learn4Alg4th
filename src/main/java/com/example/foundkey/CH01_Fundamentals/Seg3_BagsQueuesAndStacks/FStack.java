package com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FStack<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
    }

    private Node mTop;
    private int mSize;

    public FStack() {
        mTop = null;
        mSize = 0;

        assert check();
    }

    public void push(Item item) {
        Node oldTop = mTop;
        mTop = new Node();
        mTop.item = item;
        mTop.next = oldTop;
        mSize++;

        assert check();
    }

    public Item pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }

        Item item = mTop.item;
        mTop = mTop.next;
        mSize--;

        assert check();
        return item;
    }

    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack underflow");
        }

        return mTop.item;
    }

    public boolean isEmpty() {
        return mTop == null;
    }

    public int size() {
        return mSize;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item).append(" ");
        }

        return s.toString();
    }

    private boolean check() {
        if (mSize < 0) {
            return false;
        }

        if (mSize == 0) {
            if (mTop != null) {
                return false;
            }
        } else if(mSize == 1){
            if (mTop == null) {
                return false;
            }
            if (mTop.next != null) {
                return false;
            }
        } else {
            if (mTop == null) {
                return false;
            }

            if (mTop.next == null) {
                return false;
            }
        }

        int numberOfNodes = 0;
        for (Node cur = mTop; cur != null && numberOfNodes <= mSize; cur = cur.next) {
            numberOfNodes++;
        }

        return numberOfNodes == mSize;
    }

    private class FStackIterator implements Iterator<Item> {
        private Node current = mTop;

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
        return new FStackIterator();
    }
}
