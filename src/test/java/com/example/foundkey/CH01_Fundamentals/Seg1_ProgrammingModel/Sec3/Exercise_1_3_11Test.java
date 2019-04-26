package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import org.junit.Test;

import static org.junit.Assert.*;

public class Exercise_1_3_11Test {

    @Test
    public void evaluatePostfix() {
        assertEquals(27, Exercise_1_3_11.evaluatePostfix("3 4 5 + *"));
        assertEquals(277, Exercise_1_3_11.evaluatePostfix("1 2 3 4 5 * + 6 * * +"));
        assertEquals(30001, Exercise_1_3_11.evaluatePostfix("7 16 16 16 * * * 5 16 16 * * 3 16 * 1 + + +"));
        assertEquals(30001, Exercise_1_3_11.evaluatePostfix("7 16 * 5 + 16 * 3 + 16 * 1 +"));
    }
}