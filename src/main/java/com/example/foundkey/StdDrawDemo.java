package com.example.foundkey;

import edu.princeton.cs.algs4.StdDraw;

public class StdDrawDemo {
    public static void main(String[] args) {
        int n = 100;
        StdDraw.setXscale(0, n);
        StdDraw.setYscale(0, n * n);
        StdDraw.setPenRadius(0.01);


        try {
            for (int i = 0; i < n; i++) {
                StdDraw.point(i, i);
                StdDraw.point(i, i * i);
                StdDraw.point(i, i * Math.log(i));
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
