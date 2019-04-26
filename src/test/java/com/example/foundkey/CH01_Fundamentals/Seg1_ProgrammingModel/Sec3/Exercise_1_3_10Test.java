package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import org.junit.Test;

import static org.junit.Assert.*;

public class Exercise_1_3_10Test {

    @Test
    public void infixToPostfix() {
        assertEquals("2 3 4 + 5 6 * * +",
                Exercise_1_3_10.infixToPostfix("( 2 + ( ( 3 + 4 ) * ( 5 * 6 ) ) )")
        );

        assertEquals("5 7 1 1 + * + 3 * 2 1 1 + * +",
                Exercise_1_3_10.infixToPostfix("( ( ( 5 + ( 7 * ( 1 + 1 ) ) ) * 3 ) + ( 2 * ( 1 + 1 ) ) )")
        );
    }
}