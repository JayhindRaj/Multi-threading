package com.jay.multithread.thread.pool;

public class StackMemoryTest {

    public static void main(String[] args) {
        int x = 3;
        int y = 5;
        int result = sum(x, y);
        System.out.println("result: " + result);
    }

    private static int sum(int a, int b) {
        return a + b;
    }
}
