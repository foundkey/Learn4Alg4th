package com.example.foundkey;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;

public class HelloWorld {
    public static void main(String[] args) {
        StdOut.printf("%s : %d", "Hello World", 42);
        StdOut.main(new String[0]);
        Point2D.main(new String[] {"10", "10", "10"});

        In in = new In("https://algs4.cs.princeton.edu/15uf/tinyUF.txt");
        StdOut.println(in.readInt());
        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();
            StdOut.printf("%d %d\n", p, q);
        }
    }
}
