package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec1;

import edu.princeton.cs.algs4.StdOut;

public class Exercise_1_1_24 {
    public static int gcd(int p, int q) {
        StdOut.printf("p: %d, q: %d\n", p, q);
        if (q == 0) {
            return p;
        }

        int r = p % q;
        return gcd(q, r);
    }

    public static void main(String[] args) {
        int p = 1111111;
        int q = 1234567;
        StdOut.printf("gcd(%d, %d) = %d", p, q, gcd(1111111, 1234567));
    }
}
