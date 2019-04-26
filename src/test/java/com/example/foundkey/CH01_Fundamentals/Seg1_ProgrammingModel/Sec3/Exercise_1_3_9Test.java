package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import org.junit.Test;

import static org.junit.Assert.*;

public class Exercise_1_3_9Test {

    @Test
    public void fixParenthesis() {
        String fixExpress = Exercise_1_3_9.fixParenthesis("1 + 2 ) * 3 - 4 ) * 5 - 6 ) ) )");
        assertEquals("( ( 1 + 2 ) * ( ( 3 - 4 ) * ( 5 - 6 ) ) )", fixExpress);
    }
}