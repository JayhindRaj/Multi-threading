package com.jay.multithread.thread;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class ComplexCalculation {

    public static void main(String[] args) {
        BigInteger result = new ComplexCalculation().calculateResult(new BigInteger("2"), new BigInteger("10"), new BigInteger("4"), new BigInteger("5"));
        System.out.println("Result is : " + result);
    }

    public BigInteger calculateResult(BigInteger base1, BigInteger power1, BigInteger base2, BigInteger power2) {
        BigInteger result = BigInteger.ZERO;

        // Created two threads for two sets of input parameters.
        List<PowerCalculatingThread> threadList = Arrays.asList(new PowerCalculatingThread(base1, power1), new PowerCalculatingThread(base2, power2));

        // Started both the threads
        threadList.forEach(t -> {
            t.setDaemon(true);
            t.start();
        });

        // Joined both the threads
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Get the result of both threads if finished.
        for (PowerCalculatingThread t : threadList) {
            if (t.isFinished()) {
                result = result.add(t.getResult());
            }
        }

        return result;
    }

    private static class PowerCalculatingThread extends Thread {
        private BigInteger result = BigInteger.ONE;
        private BigInteger base;
        private BigInteger power;
        private Boolean isFinished = false;

        public PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
           /*
           Implement the calculation of result = base ^ power
           */
            this.result = pow(this.base, this.power);
            this.isFinished = true;
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
                result = result.multiply(base);
            }
            return result;
        }

        public BigInteger getResult() {
            return result;
        }

        public Boolean isFinished() {
            return isFinished;
        }
    }
}
