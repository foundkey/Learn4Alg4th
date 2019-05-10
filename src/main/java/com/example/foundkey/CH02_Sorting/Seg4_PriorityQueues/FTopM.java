package com.example.foundkey.CH02_Sorting.Seg4_PriorityQueues;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FStack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Transaction;

public class FTopM {

    public static void main(String[] args) {
        int M = 5;
        In in = new In("https://algs4.cs.princeton.edu/24pq/tinyBatch.txt");
//        IPriorityQueue<Transaction> pq = new FMinHeapPQ<>(M + 1);
        IPriorityQueue<Transaction> pq = new FMaxHeapPQ<>(M + 1);
        while (in.hasNextLine()) {
            pq.insert(new Transaction(in.readLine()));
            if (pq.size() > M) {
                pq.deleteTop();
            }
        }

        FStack<Transaction> stack = new FStack<>();
        while (!pq.isEmpty()) {
            stack.push(pq.deleteTop());
        }
        for (Transaction t : stack) {
            StdOut.println(t);
        }
    }
}
