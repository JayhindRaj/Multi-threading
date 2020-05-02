package com.jay.multithread.thread;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoinExample {

    public static void main(String[] args) {
        System.out.println("No Of processors: " + Runtime.getRuntime().availableProcessors());
        List<Long> inputNumbers = Arrays.asList(0L, 3L, 100000L, 123L, 345L, 12L, 456L);

        List<FactorialThread> factorialThreads = new ArrayList<>();
        inputNumbers.forEach(n -> factorialThreads.add(new FactorialThread(n)));

        factorialThreads.forEach(Thread::start);
        factorialThreads.forEach(t -> {
            try {
                t.join(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        factorialThreads.forEach(t -> {
            if (t.isFinished()) {
                System.out.println("Factorial of\t" + t.inputNumber + "\tis : " + t.getResult());
            } else {
                System.out.println("Factorial calculation for\t" + t.inputNumber + "\tis still in progress.");
            }
        });
    }

    public static class FactorialThread extends Thread {
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean isFinished = false;

        public FactorialThread(long inputNumber) {
            this.inputNumber = inputNumber;
        }

        public BigInteger factorial(long input) {
            BigInteger result = BigInteger.ONE;
            for (long i = input; i > 0; i--) {
                result = result.multiply(new BigInteger(Long.toString(i)));
            }
            return result;
        }

        @Override
        public void run() {
            this.result = factorial(this.inputNumber);
            this.isFinished = true;
        }

        public boolean isFinished() {
            return this.isFinished;
        }

        public BigInteger getResult() {
            return this.result;
        }
    }
}
