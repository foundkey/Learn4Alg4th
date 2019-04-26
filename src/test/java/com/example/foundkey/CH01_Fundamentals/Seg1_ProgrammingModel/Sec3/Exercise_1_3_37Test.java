package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import org.junit.Test;

import static org.junit.Assert.*;

public class Exercise_1_3_37Test {

    @Test
    public void josephus() {
        assertArrayEquals(new int[]{1, 3, 5, 0, 4, 2, 6}, Exercise_1_3_37.Josephus(7, 2));
    }
}