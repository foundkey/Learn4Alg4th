package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class FBag<Item> implements Iterable<Item>{
    private Node first;
    private int size;

    public FBag() {
        first = null;
        size = 0;
    }

    private class Node {
        Item item;
        Node next;
    }

    public void add(Item item) {
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        size++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    private class FBagIterator implements Iterator<Item> {
        private Node current = first;
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
        return new FBagIterator();
    }
}
