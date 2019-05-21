package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class FMultiway {

    private static void merge(In[] streams) {
        int n = streams.length;
        FAbstractIndexPQ<String> pq = new FIndexMinPQ<>(n);

        for (int i = 0; i < n; i++) {
            if (!streams[i].isEmpty()) {
                pq.insert(i, streams[i].readString());
            }
        }

        while (!pq.isEmpty()) {
            StdOut.print(pq.root() + " ");
            int i = pq.delRoot();
            if (!streams[i].isEmpty()) {
                pq.insert(i, streams[i].readString());
            }
        }
    }

    public static void main(String[] args) {
        In[] streams = {
                new In("https://algs4.cs.princeton.edu/24pq/m1.txt"),
                new In("https://algs4.cs.princeton.edu/24pq/m2.txt"),
                new In("https://algs4.cs.princeton.edu/24pq/m3.txt")
        };

        merge(streams);
    }
}
