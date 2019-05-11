package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Exercise_2_4_25 {

    private static class CubeSum implements Comparable<CubeSum> {
        private final int sum;
        private final int i;
        private final int j;

        public CubeSum(int i, int j) {
            this.sum = i * i * i + j * j * j;
            this.i = i;
            this.j = j;
        }

        @Override
        public int compareTo(CubeSum that) {
            if (this.sum < that.sum) {
                return -1;
            }

            if (this.sum > that.sum) {
                return +1;
            }

            return 0;
        }

        public String toString() {
            return sum + " = " + i + "^3" + " + " + j + "^3";
        }
    }

    public static void main(String[] args) {
        int n = 1000;

        FMinHeapPQ<CubeSum> pq = new FMinHeapPQ<>();
        for (int i = 0; i <= n; i++) {
            pq.insert(new CubeSum(i, i));
        }

        while (!pq.isEmpty()) {
            CubeSum s = pq.deleteMin();
//            StdOut.println(s);
            if (s.j < n) {
                pq.insert(new CubeSum(s.i, s.j + 1));
                CubeSum min = pq.min();
                if (s.compareTo(min) == 0) {
                    StdOut.println(s + " = " + min);
                }
            }
        }
    }
}
