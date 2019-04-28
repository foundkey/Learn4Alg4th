package com.example.foundkey;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class StopwatchCPU {

    private static final double NANOSECONDS_PER_SECOND = 1000000000;

    private final ThreadMXBean threadTimer;
    private long start;

    public StopwatchCPU() {
        threadTimer = ManagementFactory.getThreadMXBean();
        restart();
    }

    public void restart() {
        start = threadTimer.getCurrentThreadCpuTime();
    }

    public double elapsedTime() {
        long now = threadTimer.getCurrentThreadCpuTime();

        return (now - start) / NANOSECONDS_PER_SECOND;
    }
}
