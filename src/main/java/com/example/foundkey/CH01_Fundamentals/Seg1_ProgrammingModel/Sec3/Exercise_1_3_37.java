package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import java.util.ArrayList;
import java.util.List;

public class Exercise_1_3_37 {

    public static int[] Josephus(int N, int M) {
        FQueue<Integer> queue = new FQueue<>();
        for (int i = 0; i < N; i++) {
            queue.enqueue(i);
        }

        int[] result = new int[N];
        int outCount = 0;
        while (!queue.isEmpty()) {
            for (int i = 0; i < M - 1; i++) {
                queue.enqueue(queue.dequeue());
            }
            result[outCount++] = queue.dequeue();
        }

        return result;
    }
}
