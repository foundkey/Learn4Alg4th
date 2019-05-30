package com.example.foundkey.CH03_Searching.Seg3_BalancedSearchTrees;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.IOrderedSymbolTable;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class FRedBlackBST<Key extends Comparable<Key>, Value> implements IOrderedSymbolTable<Key, Value> {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private boolean color;  // color of parent link
        private int size;

        public Node(Key key, Value value, boolean color, int size) {
            this.key = key;
            this.value = value;
            this.color = color;
            this.size = size;
        }
    }

    private Node mRoot;

    @Override
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("calls puts() with null key");
        }

        if (value == null) {
            delete(key);
            return;
        }

        mRoot = put(mRoot, key, value);
        mRoot.color = BLACK;

        assert check();
    }

    private Node put(Node root, Key key, Value value) {
        if (root == null) {
            return new Node(key, value, RED, 1);
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            root.left = put(root.left, key, value);
        } else if (cmp > 0) {
            root.right = put(root.right, key, value);
        } else {
            root.value = value;
        }

        // fix-up any right-leaning links
        if (isRed(root.right) && !isRed(root.left)) {
            root = rotateLeft(root);
        }

        // root.left不存在时，isRed(root.left)返回false，根据短路求值，后面表达式不计算
        if (isRed(root.left) && isRed(root.left.left)) {
            root = rotateRight(root);
        }

        if (isRed(root.left) && isRed(root.right)) {
            flipColors(root);
        }

        // fix size
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
            throw new IllegalArgumentException("argument to get() is null");
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
            throw new IllegalArgumentException("argument to delete is null");
        }

        if (!contains(key)) {
            return;
        }

        if (!isRed(mRoot.left) && !isRed(mRoot.right)) {
            mRoot.color = RED;
        }

        mRoot = delete(mRoot, key);
        if (!isEmpty()) {
            mRoot.color = BLACK;
        }

        assert check();
    }

    private Node delete(Node root, Key key) {
        assert get(root, key) != null;

        if (key.compareTo(root.key) < 0) {
            if (!isRed(root.left) && !isRed(root.left.left)) {
                root = moveRedLeft(root);
            }
            root.left = delete(root.left, key);
        } else {
            if (isRed(root.left)) {
                root = rotateRight(root);
            }

            if (key.compareTo(root.key) == 0 && (root.right == null)) {
                return null;
            }

            if (!isRed(root.right) && !isRed(root.right.left)) {
                root = moveRedRight(root);
            }

            if (key.compareTo(root.key) == 0) {
                Node tmp = min(root.right);
                root.key = tmp.key;
                root.value = tmp.value;
                root.right = deleteMin(root.right);
            } else {
                root.right = delete(root.right, key);
            }
        }

        return balance(root);
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
            throw new NoSuchElementException();
        }

        return min(mRoot).key;
    }

    private Node min(Node root) {
        if (root.left == null) {
            return root;
        }

        return min(root.left);
    }

    @Override
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        return max(mRoot).key;
    }

    private Node max(Node root) {
        if (root.right == null) {
            return root;
        }

        return max(root.right);
    }

    @Override
    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }

        Node x = floor(mRoot, key);
        return x == null ? null : x.key;
    }

    private Node floor(Node root, Key key) {
        if (root == null) {
            return null;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            return floor(root.left, key);
        } else if (cmp > 0) {
            Node tmp = floor(root.right, key);
            return tmp == null ? root : tmp;
        } else {
            return root;
        }
    }

    @Override
    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }

        Node x = ceiling(mRoot, key);
        return x == null ? null : x.key;
    }

    private Node ceiling(Node root, Key key) {
        if (root == null) {
            return null;
        }

        int cmp = key.compareTo(root.key);
        if (cmp < 0) {
            Node tmp = ceiling(root.left, key);
            return tmp == null ? root : tmp;
        } else if (cmp > 0) {
            return ceiling(root.right, key);
        } else {
            return root;
        }
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }

        return select(mRoot, k).key;
    }

    private Node select(Node root, int k) {
        if (root == null) {
            return null;
        }

        int v = size(root.left);
        if (v < k) {
            // 目标在右子树
            return select(root.right, k - v - 1);
        } else if (v > k) {
            // 目标在左子树
            return select(root.left, k);
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
            return size(root.left) + 1 + rank(root.right, key);
        } else {
            return size(root.left);
        }
    }

    @Override
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (!isRed(mRoot.left) && !isRed(mRoot.right)) {
            mRoot.color = RED;
        }

        mRoot = deleteMin(mRoot);
        if (!isEmpty()) {
            mRoot.color = BLACK;
        }

        assert check();
    }

    private Node deleteMin(Node root) {
        if (root.left == null) {
            return null;
        }

        if (!isRed(root.left) && !isRed(root.left.left)) {
            root = moveRedLeft(root);
        }

        root.left = deleteMin(root.left);
        return balance(root);
    }

    @Override
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (!isRed(mRoot.left) && !isRed(mRoot.right)) {
            mRoot.color = RED;
        }

        mRoot = deleteMax(mRoot);
        if (!isEmpty()) {
            mRoot.color = BLACK;
        }
        assert check();
    }

    private Node deleteMax(Node root) {
        if (isRed(root.left)) {
            root = rotateRight(root);
        }

        if (root.right == null) {
            return null;
        }

        if (!isRed(root.right) && !isRed(root.right.left)) {
            root = moveRedRight(root);
        }

        root.right = deleteMax(root.right);
        return balance(root);
    }

    @Override
    public int size() {
        return size(mRoot);
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

        return contains(high) ? rank(high) - rank(low) + 1 : rank(high) - rank(low);
    }

    @Override
    public Iterable<Key> keys() {
        return isEmpty() ? new FQueue<>() : keys(min(), max());
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

        int lowCmp = low.compareTo(root.key);
        int highCmp = high.compareTo(root.key);

        if (lowCmp < 0) {
            keys(root.left, queue, low, high);
        }

        if (lowCmp <= 0 && highCmp >= 0) {
            queue.enqueue(root.key);
        }

        if (highCmp > 0) {
            keys(root.right, queue, low, high);
        }
    }

    /*
     * Node helper functions
     */
    private boolean isRed(Node node) {
        return node != null && node.color == RED;
    }

    private int size(Node node) {
        return node == null ? 0 : node.size;
    }

    /*
     *     |                 |
     *     B                 C
     *   / \\   ==>        // \
     *  A   C             B
     *     / \           / \
     *                  A
     * 左旋，红链接不增加树的高度，因此左旋后树的高度不变
     */
    private Node rotateLeft(Node node) {
        assert (node != null) && isRed(node.right);

        // fix child link
        Node tmp = node.right;
        node.right = tmp.left;
        tmp.left = node;
        // fix color
        tmp.color = tmp.left.color;
        tmp.left.color = RED;
        // fix size
        tmp.size = node.size;   // 左旋后，树的节点不变，根节点发生变化
        node.size = size(node.left) + 1 + size(node.right);

        return tmp;
    }

    /*
     *      |                  |
     *      B                  A
     *   //  \    ==>         / \\
     *   A    C                  B
     * / \                     /  \
     *                             C
     * 右旋，红链接不增加树的高度，因此右旋后树的高度不变
     */
    private Node rotateRight(Node node) {
        assert (node != null) && isRed(node.left);

        // fix child link
        Node tmp = node.left;
        node.left = tmp.right;
        tmp.right = node;
        // fix color
        tmp.color = tmp.right.color;
        tmp.right.color = RED;
        // fix size
        tmp.size = node.size;
        node.size = size(node.left) + 1 + size(node.right);

        return tmp;
    }

    private void flipColors(Node node) {
        assert (node != null) && (node.left != null) && (node.right != null);
        assert (!isRed(node) && isRed(node.left) && isRed(node.right)) ||
                (isRed(node) && !isRed(node.left) && !isRed(node.right));

        node.color = !node.color;
        node.left.color = !node.left.color;
        node.right.color = !node.right.color;
    }

    private Node moveRedLeft(Node node) {
        assert node != null;
        assert isRed(node) && !isRed(node.left) && !isRed(node.left.left);

        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }

        return node;
    }

    private Node moveRedRight(Node node) {
        assert node != null;
        assert isRed(node) && !isRed(node.right) && !isRed(node.right.left);

        flipColors(node);
        if (isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }

        return node;
    }

    private Node balance(Node node) {
        assert node != null;

        if (isRed(node.right)) {
            node = rotateLeft(node);
        }

        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }

        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    /*
     * helper functions
     */
    private boolean check() {
        if (!isBST()) {
            StdOut.println("Not in symmetric order");
            return false;
        }

        if (!isSizeConsistent()) {
            StdOut.println("Subtree counts not consistent");
            return false;
        }

        if (!isRankConsistent()) {
            StdOut.println("Ranks not consistent");
            return false;
        }

        if (!is23()) {
            StdOut.println("Not a 2-3 tree");
            return false;
        }

        if (!isBalanced()) {
            StdOut.println("Not balanced");
            return false;
        }

        return true;
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

    private boolean is23() {
        return is23(mRoot);
    }

    private boolean is23(Node root) {
        if (root == null) {
            return true;
        }

        if (isRed(root.right)) {
            return false;
        }

        if (root != mRoot && isRed(root) && isRed(root.left)) {
            return false;
        }

        return is23(root.left) && is23(root.right);
    }

    private boolean isBalanced() {
        int black = 0;
        Node cur = mRoot;
        while (cur != null) {
            if (!isRed(cur)) {
                black++;
            }
            cur = cur.left;
        }

        return isBalanced(mRoot, black);
    }

    private boolean isBalanced(Node root, int black) {
        if (root == null) {
            return black == 0;
        }

        if (!isRed(root)) {
            black--;
        }

        return isBalanced(root.left, black) && isBalanced(root.right, black);
    }
}
