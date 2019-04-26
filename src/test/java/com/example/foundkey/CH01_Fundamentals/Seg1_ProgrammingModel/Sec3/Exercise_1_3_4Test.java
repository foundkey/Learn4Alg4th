package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.Exercise_1_3_4;
import org.junit.Test;

import static org.junit.Assert.*;

public class Exercise_1_3_4Test {

    @Test
    public void parentheses() {
        assertTrue(Exercise_1_3_4.parentheses("[()]{}{[()()]()}"));
        assertFalse(Exercise_1_3_4.parentheses("[(])"));
    }
}