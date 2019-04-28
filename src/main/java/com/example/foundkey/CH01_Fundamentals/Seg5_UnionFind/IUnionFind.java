package com.example.foundkey.CH01_Fundamentals.Seg5_UnionFind;

public interface IUnionFind {

    void initial(int N);

    void union(int p, int q);

    int find(int p);

    boolean connected(int p, int q);

    int count();

    String algName();
}
