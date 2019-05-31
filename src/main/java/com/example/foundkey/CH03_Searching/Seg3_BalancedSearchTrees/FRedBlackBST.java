package com.example.foundkey.CH03_Searching.Seg3_BalancedSearchTrees;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.IOrderedSymbolTable;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/*
    (E,R)                    E == R                   R
    / | \    ===>           / \    \    ===>        // \
(A,C) H  S            A == C  H     S              E    S
                                                 / \
                                                C  H
                                              //
                                             A
红黑树就是使用二叉树来表示2-3树，用红链接来展开2-3节点中的3-节点，并满足以下要求：
    1、所有红链接都是左链接（2-3树展开时，3-节点中子树划分给小键）
    2、每个节点至多与一个红链接相连（与两个红连接相连的为4-节点，2-3树中不存在4-节点）
    3、所有空链接到根节点进过的黑链接数都相同（黑平衡，满足1、2条件自动达到）
也就是说，红黑树和2-3树可以相互转换。2-3树节点合并、拆分的变化，只需要通过红黑树的左旋、右旋、颜色反转操作即可完成。

PS1：
和普通的二叉树不同，红黑树是向上生长的树。每次插入元素后，通过左旋、右旋操作向上传递红链接。
插入完成后，会手动设置根节点的链接为黑色，根节点由红变黑时，树的高度加1。

PS2：
结论：红黑树中的每个节点，如果左节点为空，右节点必为空。
证明：插入新节点时，总是以红链接标记。如果新节点插入在右子树，必定会有左旋操作（不允许有右链接为红）。
这一特点保证红黑树中，不存在左节点为空，右节点不为空的子树。

证明2：如果左节点为空，右节点不为空，只有右节点是红色的情况下满足黑平衡。但是右节点不能为红色。
推论：红黑树中不存在只有右节点的节点

PS3：
结论：如果左叶子节点是红色的，那么该节点一定没有兄弟节点。
证明：如果有兄弟节点，那么兄弟节点的空链接无法满足黑平衡。

推论：如果左叶子节点不是红色的，那么该节点一定有一个黑色的兄弟节点
推论：没有兄弟节点的节点，必定是红色的左叶子节点。

*/
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

    /*
     * 删除操作要保证删除后仍然能保持黑平衡。因此要求删除的节点必须是红色的（红链接指向的节点）。
     * 为了保证删除的节点是红色的，删除前向下传递红链接。
     *      向下传递红链接前，先判断子树是否能为删除目标提供红链接，如果能，则不需要向下传递（判断标准是高度为2的子树红有红链接）
     *      传递红链接时，优先从兄弟节点获取。兄弟节点无红链接，从父节点展开。无论从谁获取，都先反转颜色，便于红链接传递。
     *      向右子数传递时比较特殊，需要先右旋（红黑树中无红色右链接）再传递
     * 删除结束后，再递归向上配平红链接（如同put()方法）
     */
    @Override
    public void delete(Key key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to delete is null");
        }

        /*
         * 与普通二叉搜索树不同，红黑树定位删除节点，要向下传递红链接，成本远大于判断key是否在节点中。
         * 因此删除前判断目标是否存在。
         * 并且搜索过程需要保证目标存在，才不会出现异常
         */
        if (!contains(key)) {
            return;
        }

        // 保证父节点有红链接可以向下传递
        if (!isRed(mRoot.left) && !isRed(mRoot.right)) {
            mRoot.color = RED;
        }

        mRoot = delete(mRoot, key);

        // 根节点总是黑色的
        if (!isEmpty()) {
            mRoot.color = BLACK;
        }

        assert check();
    }

    private Node delete(Node root, Key key) {
        assert get(root, key) != null;

        if (key.compareTo(root.key) < 0) {
            // 目标在左子树
            if (!isRed(root.left) && !isRed(root.left.left)) {
                // 高度为2的左子树中没有红链接，需要向下传递
                root = moveRedLeft(root);
            }
            root.left = delete(root.left, key);
        } else {
            // 红链接总是存在左链接中，要右旋后才能向右子树传递
            if (isRed(root.left)) {
                root = rotateRight(root);
            }

            if (key.compareTo(root.key) == 0 && (root.right == null)) {
                /*
                 * 找到删除目标，且目标没有左右子树可以直接删除
                 * 推导：如果root没有右节点，说明上一步没有进行右旋操作（右旋后必有右节点）
                 * 没有发生右旋操作证明root的左节点不是红色的，
                 * root的左节点如果不是红色，那么root的左右节点要么同时存在，要是同时不存在。
                 *
                 * 因为递归入口保证目标节点肯定存在，所有不存在不是目标，右节点为空的情况。
                 */
                return null;
            }

            if (!isRed(root.right) && !isRed(root.right.left)) {
                // 高度为2的右子树中没有红链接，需要向下传递
                root = moveRedRight(root);
            }

            if (key.compareTo(root.key) == 0) {
                /*
                 * 找到目标删除，方法与二叉搜索树一样
                 *  1、在右子树中找到后继节点
                 *  2、替换当前节点为后继节点（key、value，不包含其他）
                 *  3、在右子树中删除后继节点。
                 *
                 */
                Node tmp = min(root.right);
                root.key = tmp.key;
                root.value = tmp.value;
                root.right = deleteMin(root.right);
            } else {
                // 目标在右子树中
                root.right = delete(root.right, key);
            }
        }

        // 删除后配平红链接
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

    /*
     * 红黑树本质上还是一颗二叉平衡树，只是通过标记红链接达到黑平衡（所有空链接到根节点经过的黑链接数相同）。
     * 二叉树的有序相关接口无链接的红黑属性无关，因此用于二叉平衡树的有序接口（min()、max()、floor()、ceiling()、select()、rank()），
     * 可以在不修改的情况下，直接用于红黑树。
     */
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

    /*
     * 红黑树删除节点时，为了保证黑平衡，必须从红链接节点。
     * 因为删除红链接节点不影响树的高度，如果删除的是黑链接节点，该分支下的空链接高度会减1，破坏黑平衡。
     * 因此，删除前要向下传递红链接（与插入正好相反），删除完成后再把红链接配平（与插入完成时相同）
     */
    @Override
    public void deleteMin() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // 如果子树中没用红链接，根结点设置为红链接，防止出现没有红链接向下传递的情况
        if (!isRed(mRoot.left) && !isRed(mRoot.right)) {
            mRoot.color = RED;
        }

        mRoot = deleteMin(mRoot);

        // 根节点总是黑色
        if (!isEmpty()) {
            mRoot.color = BLACK;
        }

        assert check();
    }

    private Node deleteMin(Node root) {
        if (root.left == null) {
            /*
             * 红黑树的每个节点如果左节点为空，右节点必定为空。不需要考虑二叉树中，删除节点带有右节点的情况。
             * 被删除的节点总是红色的（红链接向下传递的结果）
             */
            return null;
        }


        /*
         * 高度为2左子树中有红链接，无需向下传递红链接
         */
        if (!isRed(root.left) && !isRed(root.left.left)) {
            // 删除前向下（左子树）传递红链接，保证删除时，删除节点是红链接。（删除节点不破坏黑平衡的保证）
            root = moveRedLeft(root);
        }

        root.left = deleteMin(root.left);
        return balance(root);   // 删除完成后，向上配平红链接
    }

    /*
     * 与删除最小节点有所不同。最大节点都在右子树上，而右链接必定是黑色的。删除节点的操作又必须在红链接下才能进行。
     */
    @Override
    public void deleteMax() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // 如果子树中没用红链接，根结点设置为红链接，防止出现没有红链接向下传递的情况
        if (!isRed(mRoot.left) && !isRed(mRoot.right)) {
            mRoot.color = RED;
        }

        mRoot = deleteMax(mRoot);

        // 根节点的颜色始终为黑色
        if (!isEmpty()) {
            mRoot.color = BLACK;
        }
        assert check();
    }

    private Node deleteMax(Node root) {
        // 红黑树红链接都是左链接，为了让红链接能在右子树中向下传递，需要右旋
        if (isRed(root.left)) {
            root = rotateRight(root);
        }

        if (root.right == null) {
            /*
             * 不存在右节点，那么当前节点为最大节点，且没有左节点，可以直接删除
             * 推导：如果root没有右节点，说明上一步没有进行右旋操作（右旋后必有右节点）
             * 没有发生右旋操作证明root的左节点不是红色的，
             * root的左节点如果不是红色，那么root的左右节点要么同时存在，要是同时不存在。
             */
            return null;
        }

        if (!isRed(root.right) && !isRed(root.right.left)) {
            // 向下（右子树）传递红链接
            root = moveRedRight(root);
        }

        root.right = deleteMax(root.right);
        return balance(root);   // 删除后配平红链接
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
     *   / \\   ===>       // \
     *  A   C             B
     *     / \           / \
     *                  A
     * 左旋，红链接不增加树的高度，因此左旋后树的高度不变。
     *
     *  另外一种视图（2-3树）：
     *     |                         |
     *     B == C               B == C
     *    /    / \     ===>   / \    \
     *   A                    A
     *  / \                 / \
     * 更容易看出来，旋转操作就是交替红链接两个节点的父子关系。
     * 旋转不会修改红黑树对应的2-3树。
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
     * 右旋，红链接不增加树的高度，因此右旋后树的高度不变。
     *
     * 另外一种视图（2-3树）：
     *    |                         |
     *    A == B               A == B
     *   /    / \     ==>     / \    \
     *           C                    C
     *          / \                  / \
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

    /*
     * 向左子树传递红链接，分两种情况（左节点肯定是黑节点，没有才需要传递）
     * 红链接传递的过程，对应的2-3树只有节点合并、拆分变化。2-3树节点合并、拆分只需要左旋、右旋、颜色反转操作就可以完成。
     * 变化的过程中，也总是保证黑平衡的
     *
     *  情况A（右节点的左节点是黑节点，不执行if语句）
     *     ||                          |
     *      C                          C
     *     /\          ===>          //  \\
     *    B  E    （C颜色反转）     B     E
     *
     *  情况B（右节点的左节点不是黑节点，执行if语句）
     *      ||                     |                       |                     |                       ||
     *       C                     C                       C                     D                       D
     *      / \        ===>      // \\       ===>       // \\      ===>       // \\       ===>         / \
     *     B   E   （C颜色反转） B   E     (E右旋）     B   D    （C左旋）   C    E   （D颜色反转）   C  E
     *    /\ // \               /\ // \                /\  / \\            // \  /\                // \ /\
     *      D                     D                           E           B                       B
     * 总结：兄弟有，向兄弟要；兄弟没有，向父亲要；父亲给时，要雨露均沾。
     */
    private Node moveRedLeft(Node node) {
        assert node != null;
        assert isRed(node) && !isRed(node.left) && !isRed(node.left.left);

        // 必须先反转颜色，不然情况B中的左旋操作无法完成
        flipColors(node);
        if (isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }

        return node;
    }

    /*
     * 向右子树传递红链接，分两种情况（右节点肯定是黑节点，没有才需要传递）
     * 红链接传递的过程，对应的2-3树只有节点合并、拆分变化。2-3树节点合并、拆分只需要左旋、右旋、颜色反转操作就可以完成。
     * 变化的过程中，也总是保证黑平衡的
     *
     *  情况A（左节点的左节点是黑节点，不执行if语句）
     *     ||                          |
     *      C                          C
     *     /\          ===>          //  \\
     *    B  E    （C颜色反转）     B     E
     *
     *  情况B（左节点的左节点不是黑节点，执行if语句）
     *      ||                     |                       |                        ||
     *       C                     C                       B                        B
     *      / \        ===>      // \\       ===>       // \\        ===>         / \
     *     B   D   （C颜色反转） B   D     (C右旋）     A   C    （B颜色反转）   A   C
     *   // \ / \              // \ / \                /\  / \\                / \  / \\
     *  A                     A                               D                        D
     * 总结：兄弟有，向兄弟要；兄弟没有，向父亲要；父亲给时，要雨露均沾。
     */
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

        /*
         * Q：为什么不需要像put()方法中判断左链接不为红链接？
         * A：正常情况下红黑树不存在为红色的右链接。只有在删除过程向下传递红链接时，会临时存在为红色的右链接。
         *    这些右链接都是从红色的左链接右旋而来，所有配平红链接时，拥有红色右链接的节点，左链接肯定不是红色的。
         */
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
