package com.example.foundkey.CH03_Searching;

import com.example.foundkey.CH01_Fundamentals.Seg2_DataAbstraction.VisualAccumulator;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.FBinarySearchST;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.FSequentialSearchST;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.IOrderedSymbolTable;
import com.example.foundkey.CH03_Searching.Seg1_ElementarySymbolTables.ISymbolTable;
import com.example.foundkey.Stopwatch;
import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.StdOut;

public class FFrequencyCounter {

    public static void main(String[] args) {
        /*
         * test data from:
         *    https://algs4.cs.princeton.edu/31elementary/tale.txt
         *    https://algs4.cs.princeton.edu/31elementary/leipzig100K.txt
         *    https://algs4.cs.princeton.edu/31elementary/leipzig300K.txt
         *    https://algs4.cs.princeton.edu/31elementary/leipzig1M.txt
         */
//        In in = new In("testData/taleTiny.txt");
        In in = new In("testData/tale.txt");    // 5131 14350 (不重复单词数， 总单词数）
//        In in = new In("testData/leipzig100K.txt"); // 74631 423764
//        In in = new In("testData/leipzig300K.txt"); // 146075 1270973
//        In in = new In("testData/leipzig1M.txt");   // 299593 4239597

        int minLen = 8;
        int distinct = 0;
        int words = 0;
//        ISymbolTable<String, Integer> st = new FSequentialSearchST<>(); // 只能处理到tale.txt(1.313s)
        IOrderedSymbolTable<String, Integer> st = new FBinarySearchST<>();  // tale.txt(0.347s)
//        RedBlackBST<String, Integer> st = new RedBlackBST<>();
//        BST<String, Integer> st = new BST<>();

//        VisualAccumulator accumulator = new VisualAccumulator(15000, 6000);

        Stopwatch stopwatch = new Stopwatch();
        while (!in.isEmpty()) {
            String key = in.readString();
            if (key.length() < minLen) {
                continue;
            }

            words++;

            int compareCount = 0;
            if (st.contains(key)) {
//                compareCount = st.putX(key, st.get(key) + 1);
                st.put(key, st.get(key) + 1);
            } else {
//                compareCount = st.putX(key, 1);
                st.put(key, 1);
                distinct++;
            }

//            accumulator.addDataValue(compareCount);
        }
        double time = stopwatch.elapsedTime();

        // find a key with the highest frequency count
        String max = " ";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max)) {
                max = word;
            }
        }

        StdOut.println(max + " " + st.get(max));
        StdOut.println("distinct = " + distinct);
        StdOut.println("words    = " + words);
        StdOut.println("time = " + time);
    }
}
