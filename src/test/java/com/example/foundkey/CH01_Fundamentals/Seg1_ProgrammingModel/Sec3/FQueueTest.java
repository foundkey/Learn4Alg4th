package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import com.example.foundkey.CH01_Fundamentals.Seg3_BagsQueuesAndStacks.FQueue;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FQueueTest {

    private String[] testData;

    @Before
    public void setUp() {
        testData = "Apple Banana Cherry Watermelon".split(" ");
    }

    @Test
    public void useCase() {
        FQueue<String> queue = new FQueue<>();

        for (String str : testData) {
            queue.enqueue(str);
        }

        Assert.assertEquals(testData.length, queue.size());

        StringBuilder info = new StringBuilder();
        while (!queue.isEmpty()) {
            info.append(queue.dequeue());
        }

        Assert.assertEquals("AppleBananaCherryWatermelon", info.toString());
    }

    @Test
    public void iterator() {
        FQueue<String> queue = new FQueue<>();

        for (String str : testData) {
            queue.enqueue(str);
        }

        StringBuilder info = new StringBuilder();
        for (String str : queue) {
            info.append(str);
        }

        Assert.assertEquals("AppleBananaCherryWatermelon", info.toString());
    }

    @Test
    public void enqueue() {
        FQueue<Integer> queue = new FQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void dequeue() {
        FQueue<Integer> queue = new FQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        int item = queue.dequeue();
        assertEquals(1, item);
        assertEquals(2, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void peek() {
        FQueue<Integer> queue = new FQueue<>();

        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        int item = queue.peek();
        assertEquals(1, item);
        assertEquals(3, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void isEmpty() {
        FQueue<Integer> queue = new FQueue<>();

        assertTrue(queue.isEmpty());

        queue.enqueue(1);
        assertFalse(queue.isEmpty());

        queue.dequeue();
        assertTrue(queue.isEmpty());
    }
}