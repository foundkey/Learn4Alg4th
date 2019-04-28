package com.example.foundkey.CH01_Fundamentals.Seg5_UnionFind;

import java.util.Arrays;

public class FWeightedQuickUnion implements IUnionFind {

    private int[] id;
    private int[] sz;
    private int count;

    @Override
    public void initial(int N) {
        count = N;

        id = new int[N];
        Arrays.setAll(id, i -> i);

        sz = new int[N];
        Arrays.fill(sz, 1);
    }

    @Override
    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) {
            return;
        }

        // 根据权重合并两颗联通树
        if (sz[pRoot] < sz[qRoot]){
            // p树较小，将q树合并到q树上
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        } else {
            // q树较小，将p树合并到p树上
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }

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
