package com.example.foundkey.CH01_Fundamentals.Seg5_UnionFind;

import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IUnionFindTest {

    // 测试数据地址
    private String TINY_UF_TXT_URL = "https://algs4.cs.princeton.edu/15uf/tinyUF.txt";

    private String MEDIUM_UF_TXT_URL = "https://algs4.cs.princeton.edu/15uf/mediumUF.txt";

    private String LARGE_UF_TXT_URL = "https://algs4.cs.princeton.edu/15uf/largeUF.txt";

    // 测试数据结果，使用setUp函数计算
    private int tinyComponents = 2;

    private int mediumComponents = 3;

    private int largeComponents = 6;

    /*
    * 计算三组测试数据的结果
     */
//    @Before
//    public void setUp() {
//        tinyComponents = getComponents(TINY_UF_TXT_URL);
//        mediumComponents = getComponents(MEDIUM_UF_TXT_URL);
////        largeComponents = getComponents(LARGE_UF_TXT_URL);
//    }
//
//    private static int getComponents(String testData) {
//        In in = new In(testData);
//        int n = in.readInt();
//        UF uf = new UF(n);
//        while (!in.isEmpty()) {
//            int p = in.readInt();
//            int q = in.readInt();
//            if (uf.connected(p, q)) continue;
//            uf.union(p, q);
////            StdOut.println(p + " " + q);
//        }
//        int components = uf.count();
//        System.out.println(testData + ": " + components);
//
//        return components;
//    }

    @Test
    public void testFQuickFind() {
        testAlg(new FQuickFind());
    }

    @Test
    public void testFQuickUnion() {
        testAlg(new FQuickUnion());
    }

    @Test
    public void testFWeightedQuickUnion() {
        testAlg(new FWeightedQuickUnion());
    }

    private void testAlg(IUnionFind uf) {
        Assert.assertEquals(tinyComponents, testAlg(uf, TINY_UF_TXT_URL));
        Assert.assertEquals(mediumComponents, testAlg(uf, MEDIUM_UF_TXT_URL));
        Assert.assertEquals(largeComponents, testAlg(uf, LARGE_UF_TXT_URL));
    }


    private int testAlg(IUnionFind uf, String testData) {
        In in = new In(testData);
        Stopwatch stopwatch = new Stopwatch();
        int n = in.readInt();
        uf.initial(n);
        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
//            StdOut.println(p + " " + q);
        }

        StdOut.printf("%s: %s: %.3fs\n", uf.algName(), testData, stopwatch.elapsedTime());

        return uf.count();
    }

}