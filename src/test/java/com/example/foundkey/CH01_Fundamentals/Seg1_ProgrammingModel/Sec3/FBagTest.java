package com.example.foundkey.CH01_Fundamentals.Seg1_ProgrammingModel.Sec3;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FBagTest {
    private double[] testData;

    @Before
    public void setUp() throws Exception {
        testData = new double[] {
                10, 20, 50, 100, 105,
                56, 89, 68, 99, 33
        };
    }

    @Test
    public void useCase() {
        FBag<Double> numbers = new FBag<>();

        for (double num : testData) {
            numbers.add(num);
        }

        int N = numbers.size();
        Assert.assertEquals(testData.length, N);

        double sum = 0.0;
        for (double num : numbers) {
            sum += num;
        }
        double mean = sum / N;

        StdOut.printf("Sum: %.2f, Mean: %.2f\n", sum, mean);
    }
}