package com.example.foundkey.CH01_Fundamentals.Seg5_UnionFind;

/*
* 所有的联通量指向同一个root
 */
public class FQuickUnion implements IUnionFind {

    private int[] id;
    private int count;

    @Override
    public void initial(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }

        // 合并两颗联通树
        id[pRoot] = qRoot;

        count--;
    }

    @Override
    public int find(int p) {
        // 回溯到root
        while (p != id[p]) {
            p = id[p];
        }

        return p;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int count() {
        return count;
    }

    @Override
    public String algName() {
        return this.getClass().getSimpleName();
    }
}
