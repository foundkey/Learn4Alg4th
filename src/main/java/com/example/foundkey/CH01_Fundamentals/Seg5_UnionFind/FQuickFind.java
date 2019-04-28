package com.example.foundkey.CH01_Fundamentals.Seg5_UnionFind;

import java.util.stream.Stream;

/*
* 所有联通量，使用同一个id
 */
public class FQuickFind implements IUnionFind {

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
        int pID = find(p);
        int qID = find(q);

        if (pID == qID) {
            return;
        }

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) {
                // 使用qID覆盖，表示联通
                id[i] = qID;
            }
        }

        count--;
    }

    @Override
    public int find(int p) {
        return id[p];
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
