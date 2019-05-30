package com.example.foundkey.CH03_Searching.Seg2_BinarySearchTrees;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.IOrderedSymbolTable;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class FBinarySearchTree<Key extends Comparable<Key>, Value> implements IOrderedSymbolTable<Key, Value> {

    private class Node {
        private Key key;
        private Value value;
        private Node left;  // left subtree
        private Node right; // right subtree
        private int size;   // number of nodes in subtrees

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    private Node mRoot; // root of BST

    @Override
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }

        if (value == null) {
            delete(key);
            return;
        }

        mRoot = put(mRoot, key, value);
        assert check();
    }

    private Node put(Node root, Key key, Value value) {
        if (root == null) {
            // key不能在键种，新建节点
            return new Node(key, value, 1);
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = put(root.left, key, value);
        } else if (cmp > 0) {
            root.right = put(root.right, key, value);
        } else {
            root.value = value;
        }

        root.size = size(root.left) + 1 + size(root.right);
        return root;
    }

    @Override
    public int putX(Key key, Value value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }

        return get(mRoot, key);
    }

    private Value get(Node root, Key key) {
        if (root == null) {
            return null;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            return get(root.left, key);
        } else if (cmp > 0) {
            return get(root.right, key);
        } else {
            return root.value;
        }
    }

    @Override
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("calls delete() with a null key");
        }
        mRoot = delete(mRoot, key);
        assert check();
    }

    private Node delete(Node root, Key key) {
        if (root == null) {
            return null;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = delete(root.left, key);
        } else if (cmp > 0) {
            root.right = delete(root.right, key);
        } else {
            // 左子树为空、右子树为空、左右子树都为空的情况
            if (root.right == null) {
                return root.left;
            }

            if (root.left == null) {
                return root.right;
            }

            // 左右子树都不为空的情况
            // 书中的实现
//            Node tmp = root;
//            root = min(tmp.right);  // 找到后继节点（右子树中的最小节点）
//            root.right = deleteMin(tmp.right);  // 删除后继节点，并修改新节点的右子树
//            root.left = tmp.left;   // 修改新节点的左子树
            /*
             * 另外一种实现
             *  1、找到后继节点（右子树中的最小节点）
             *  2、替换删除节点的为后继节点（key、value，不包括左右子树连接）
             *  3、删除后继节点
             *
             * 两种实现的思路都是一致的：在右子树中找到后继节点，补充到删除节点的位置
             */
            Node min = min(root.right);
            root.key = min.key;
            root.value = min.value;
            root.right = deleteMin(root.right);
        }

        root.size = size(root.left) + 1 + size(root.right);
        return root;
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
    public Key min() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls min() with empty symbol table");
        }
        return min(mRoot).key;
    }

    private Node min(Node root) {
        if (root.left == null) {
            return root;
        } else {
            return min(root.left);
        }
    }

    @Override
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("calls max() with empty symbol table");
        }
        return max(mRoot).key;
    }

    private Node max(Node root) {
        if (root.right == null) {
            return root;
        } else {
            return max(root.right);
        }
    }

    @Override
    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }

        if (isEmpty()) {
            throw new NoSuchElementException("calls floor() with empty symbol table");
        }

        Node x = floor(mRoot, key);
        if (x == null) {
            return  null;
        } else {
            return x.key;
        }
    }

    private Node floor(Node root, Key key) {
        if (root == null) {
            return null;
        }
        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root;
        }

        if (cmp < 0) {
            // key比当前节点小，那么floor(key)肯定在左子树中
            return floor(root.left, key);
        }

        // key比当前节点大，那么floor(key)可能在右子树中（不在右子树中就是当前节点）
        Node tmp = floor(root.right, key);
        if (tmp != null) {
            // 右子树中存在tmp节点，满足 root.key < tmp.key <= key，那么tmp.key为floor(key)
            return tmp;
        } else {
            // 右子树中找不到比当前节点大的节点，那么当前节点为floor(key)
            return root;
        }
    }

    @Override
    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }

        if (isEmpty()) {
            throw new NoSuchElementException("calls ceiling() with empty symbol table");
        }

        Node x = ceiling(mRoot, key);
        if (x == null) {
            return null;
        } else {
            return x.key;
        }
    }

    private Node ceiling(Node root, Key key) {
        if (root == null) {
            return null;
        }

        int cmp = key.compareTo(root.key);
        if (cmp == 0) {
            return root;
        }

        if (cmp < 0) {
            // key比当前节点小，ceiling(key)可能在左子树中（不在左子树中就是当前节点）
            Node tmp = ceiling(root.left, key);
            if (tmp != null) {
                // 左子树中存在tmp节点小于当前节点，那么tmp节点为ceiling(key)
                return tmp;
            } else {
                // 左子树中找不到比当前节点小的节点，那么当前节点为ceiling(key)
                return root;
            }
        }

        // key比当前节点大，ceiling(key)肯定在右子树
        return ceiling(root.right, key);
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }

        Node x = select(mRoot, k);
        return x.key;
    }

    private Node select(Node root, int k) {
        if (root == null) {
            return null;
        }

        int t = size(root.left);
        if (t > k) {
            // 在左子树
            return select(root.left, k);
        } else if (t < k) {
            /* 在右子树
             * k - t - 1 : 减去左子树及根节点的数量
             */
            return select(root.right, k - t - 1);
        } else {
            return root;
        }
    }

    @Override
    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }
        return rank(mRoot, key);
    }

    private int rank(Node root, Key key) {
        if (root == null) {
            return 0;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            return rank(root.left, key);
        } else if (cmp > 0) {
            return 1 + size(root.left) + rank(root.right, key);
        } else {
            return size(root.left);
        }
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        mRoot = deleteMin(mRoot);
        assert check();
    }

    private Node deleteMin(Node root) {
        if (root.left == null) {
            return root.right;
        }

        // 如果左子树没左节点，将左子树的右节点（可以为空）作为当前树的新左节点
        root.left = deleteMin(root.left);
        root.size = size(root.left) + 1 + size(root.right);
        return root;
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        mRoot = deleteMax(mRoot);
        assert check();
    }

    private Node deleteMax(Node root) {
        if (root.right == null) {
            return root.left;
        }
        root.right = deleteMax(root.right);
        root.size = size(root.left) + 1 + size(root.right);
        return root;
    }

    @Override
    public int size() {
        return size(mRoot);
    }

    private int size(Node root) {
        if (root == null) {
            return 0;
        }

        return root.size;
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

        if (contains(high)) {
            return rank(high) - rank(low) + 1;
        } else {
            return rank(high) - rank(low);
        }
    }


    @Override
    public Iterable<Key> keys() {
        if (isEmpty()) {
            return new FQueue<>();
        }
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
        keys(mRoot, queue, low, high);
        return queue;
    }

    private void keys(Node root, FQueue<Key> queue, Key low, Key high) {
        if (root == null) {
            return;
        }

        int cmpLow = low.compareTo(root.key);
        int cmpHigh = high.compareTo(root.key);

        // 限定范围的中序遍历
        if (cmpLow < 0) {
            keys(root.left, queue, low, high);
        }

        if (cmpLow <= 0 && cmpHigh >= 0) {
            queue.enqueue(root.key);
        }

        if (cmpHigh > 0) {
            keys(root.right, queue, low, high);
        }
    }

    public int height() {
        return height(mRoot);
    }

    // 拥有一个节点的树，高度是为0
    private int height(Node root) {
        if (root == null) {
            return -1;
        }

        return 1 + Math.max(height(root.left), height(root.right));
    }

    /*
     * Helper function
     */
    private boolean check() {
        return isBST() && isSizeConsistent() && isRankConsistent();
    }

    private boolean isBST() {
        return isBST(mRoot, null, null);
    }

    private boolean isBST(Node root, Key min, Key max) {
        if (root == null) {
            return true;
        }

        if (min != null && root.key.compareTo(min) <= 0) {
            return false;
        }

        if (max != null && root.key.compareTo(max) >= 0) {
            return false;
        }

        return isBST(root.left, min, root.key) && isBST(root.right, root.key, max);
    }

    private boolean isSizeConsistent() {
        return isSizeConsistent(mRoot);
    }

    private boolean isSizeConsistent(Node root) {
        if (root == null) {
            return true;
        }

        if (root.size != size(root.left) + 1 + size(root.right)) {
            return false;
        }

        return isSizeConsistent(root.left) && isSizeConsistent(root.right);
    }

    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }

        for (Key key : keys()) {
            if (key.compareTo(select(rank(key))) != 0) {
                return false;
            }
        }

        return true;
    }
}
