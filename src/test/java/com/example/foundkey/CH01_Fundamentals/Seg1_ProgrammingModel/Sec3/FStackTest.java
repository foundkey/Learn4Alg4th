package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FStack;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class FStackTest {
    private String[] testData;

    @Before
    public void setUp() {
        testData = new String[]{
                "Apple", "Banana", "Cherry", "Watermelon"
        };
    }

    @Test
    public void useCase() {
        FStack<String> stack = new FStack<>();

        for (String str : testData) {
            stack.push(str);
        }

        Assert.assertEquals(testData.length, stack.size());

        StringBuilder info = new StringBuilder();
        while (!stack.isEmpty()) {
            info.append(stack.pop());
        }
        Assert.assertEquals("WatermelonCherryBananaApple", info.toString());
    }

    @Test(expected = NoSuchElementException.class)
    public void popEmptyStack(){
        FStack<Integer> stack = new FStack<>();

        stack.pop();
    }

    @Test
    public void testIterator() {
        FStack<String> stack = new FStack<>();

        for (String str : testData) {
            stack.push(str);
        }

        StringBuilder info = new StringBuilder();
        for (String str : stack) {
            info.append(str);
        }

        Assert.assertEquals("WatermelonCherryBananaApple", info.toString());
     }

    @Test
    public void peek() {
        FStack<Integer> stack = new FStack<>();

        stack.push(42);
        stack.push(24);
        stack.push(34);
        int item = stack.peek();

        Assert.assertEquals(34, item);
        Assert.assertEquals(3, stack.size());
    }
}