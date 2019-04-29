package com.example.foundkey.CH02_Sorting.Visualizing;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

/******************************************************************************
 *  Compilation:  javac InsertionBars.java
 *  Execution:    java InsertionBars N
 *  Dependencies: StdDraw.java
 *  
 *  Insertion sort n random real numbers between 0 and 1, visualizing
 *  the results by ploting bars with heights proportional to the values.
 *
 *  % java InsertionBars 20
 *
 ******************************************************************************/


public class InsertionBars {
    public static void sort(double[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) { 
            int j = i;
            while (j >= 1 && less(a[j], a[j-1])) {
                exch(a, j, j-1);
                j--;
            }

            show(a, i, j);
        }
    }

    // 基于官网的程序，修改成动画
    private static void show(double[] a, int i, int j) {
//        StdDraw.setYscale(-a.length + i + 1, i);
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
        for (int k = 0; k < a.length; k++)
            StdDraw.line(k, 0, k, a[k]*0.6);
        StdDraw.setPenColor(StdDraw.BOOK_RED);
        delayDrawLine(j, 0, j, a[j]*0.6);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int k = j+1; k <= i; k++)
            delayDrawLine(k, 0, k, a[k]*0.6);
//        StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
//        for (int k = i+1; k < a.length; k++)
//            delayDrawLine(k, 0, k, a[k]*0.6);
    }

    private static void delayDrawLine(double x0, double y0, double x1, double y1) {
        StdDraw.line(x0, y0, x1, y1);
        StdDraw.pause(200);
        StdDraw.show();
    }

    private static boolean less(double v, double w) {
        return v < w;
    }

    private static void exch(double[] a, int i, int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdDraw.setCanvasSize(320, 160);
        StdDraw.setXscale(-1, n+1);
        StdDraw.setPenRadius(0.012);
        StdDraw.enableDoubleBuffering();
        double[] a = new double[n];
        for (int i = 0; i < n; i++)
            a[i] = StdRandom.uniform(0.0, 1.0);
        sort(a);
    }

}
