package com.example.foundkey.CH03_Searching.Seg2_BinarySearchTrees;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;
import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FStack;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.IOrderedSymbolTable;

import java.util.NoSuchElementException;

public class FBinarySearchTreeNonRecursive<Key extends Comparable<Key>, Value> implements IOrderedSymbolTable<Key, Value> {

    private class Node {
        private Key key;
        private Value value;
        private Node left;  // left subtree
        private Node right; // right subtree

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node mRoot; // root of BST
    private int mSize;

    @Override
    public void put(Key key, Value value) {
        if (key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }

        if (value == null) {
            delete(key);
            return;
        }

        Node tmp = new Node(key, value);
        if (mRoot == null) {
            mRoot = tmp;
            mSize++;
            return;
        }

        Node parent = null;
        Node cur = mRoot;
        while (cur != null) {
            parent = cur;
            int cmp = key.compareTo(cur.key);
            if (cmp < 0) {
                cur = cur.left;
            } else if (cmp > 0) {
                cur = cur.right;
            } else {
                cur.value = value;
                return;
            }
        }

        int cmp = key.compareTo(parent.key);
        if (cmp < 0) {
            parent.left = tmp;
        } else {
            parent.right = tmp;
        }
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

        Node cur = mRoot;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp < 0) {
                cur = cur.left;
            } else if (cmp > 0) {
                cur = cur.right;
            } else {
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

        Node parent = null;
        boolean isLeft = true; // 父节点来自左子树
        Node cur = mRoot;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp < 0) {
                parent = cur;
                isLeft = true;
                cur = cur.left;
            } else if (cmp > 0) {
                parent = cur;
                isLeft = false;
                cur = cur.right;
            } else {
                break;
            }
        }

        if (cur != null) {
            Node tmp = null;
            if (cur.right == null) {
                tmp = cur.left;
            } else if (cur.left == null) {
                tmp = cur.right;
            } else {
                tmp = minNode(cur.right);
                cur.right = deleteMin(cur.right);
                tmp.left = cur.left;
                tmp.right = cur.right;
            }

            if (parent == null) {
                mRoot = tmp;
            } else {
                if (isLeft) {
                    parent.left = tmp;
                } else {
                    parent.right = tmp;
                }
            }
        }

        mSize--;
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
    public Key min() {
        return minNode(mRoot).key;
    }

    private Node minNode(Node root) {
        Node cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }

        return cur;
    }

    @Override
    public Key max() {
        return maxNode(mRoot).key;
    }

    private Node maxNode(Node root) {
        Node cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }

        return cur;
    }

    @Override
    public Key floor(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }

        if (isEmpty()) {
            throw new NoSuchElementException("calls floor() with empty symbol table");
        }

        Node cur = mRoot;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp < 0) {
                cur = cur.left;
            } else if (cmp > 0) {
                if (cur.right == null) {
                    // cur不存在右子树，那么当前节点为floor(key)
                    break;
                } else if (key.compareTo(cur.right.key) < 0) {
                    // cur有右子树，但右子树根节点大于key，那么当前节点为floor(key)
                    break;
                } else {
                    // cur有右子树，但右子树根节点小于等于key，那么floor(key)在右子树中
                    cur = cur.right;
                }

            } else {
                break;
            }
        }

        return cur == null ? null : cur.key;
    }

    @Override
    public Key ceiling(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to ceiling() is null");
        }

        if (isEmpty()) {
            throw new NoSuchElementException("calls ceiling() with empty symbol table");
        }

        Node cur = mRoot;
        while (cur != null) {
            int cmp = key.compareTo(cur.key);
            if (cmp > 0) {
                cur = cur.right;
            } else if (cmp < 0) {
                if (cur.left == null) {
                    // cur不存在左子树，那么当前节点为ceiling(key)
                    break;
                } else if (key.compareTo(cur.left.key) > 0) {
                    // cur有左子树，但左子树根节点小于key，那么当前节点为ceiling(key)
                    break;
                } else {
                    // cur有左子树，但左子树根节点大于等于key，那么ceiling(key)在右子树中
                    cur = cur.right;
                }
            } else {
                break;
            }
        }

        return cur == null ? null : cur.key;
    }

    @Override
    public Key select(int k) {
        if (k < 0 || k >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid: " + k);
        }

        int count = 0;
        Key target = null;
        for (Key key : keys()) {
            if (count++ == k) {
                target = key;
                break;
            }
        }

        return target;
    }

    @Override
    public int rank(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to rank() is null");
        }

        int count = 0;
        for (Key k : keys()) {
            if (k.compareTo(key) >= 0) {
                break;
            }
            count++;
        }

        return count;
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
        // 最小值为根节点
        if (root.left == null) {
            mSize--;
            return root.right;
        }

        // 最小值为非根节点
        Node cur = root;
        while (cur.left.left != null) {
            cur = cur.left;
        }
        cur.left = cur.left.right;
        mSize--;
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
        // 最大值为根节点
        if (root.right == null) {
            mSize--;
            return root.left;
        }

        // 最大值为非根节点
        Node cur = root;
        while (cur.right.right != null) {
            cur = cur.right;
        }
        cur.right = cur.right.left;
        mSize--;
        return root;
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

        if (contains(high)) {
            return rank(high) - rank(low) + 1;
        } else {
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
        FStack<Node> stack = new FStack<>();
        Node cur = mRoot;
        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                if (low.compareTo(cur.key) > 0) {
                    // 放弃小于low的节点
                    cur = null;
                } else {
                    stack.push(cur);
                    cur = cur.left;
                }
            }

            if (!stack.isEmpty()) {
                cur = stack.pop();
                queue.enqueue(cur.key);
                cur = cur.right;

                if (cur != null && high.compareTo(cur.key) < 0) {
                    // 放弃大于high的节点
                    cur = null;
                }
            }
        }

        return queue;
    }

    /*
     * Helper function
     */
    private boolean check() {
        return isBST();
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
}
