package com.jay.multithread.deadlock;

import java.util.Random;

public class DeadlockExample {
    public static void main(String[] args) {
        InterSection interSection = new InterSection();
        TrainA trainA = new TrainA(interSection);
        trainA.setName("Train-A");
        TrainB trainB = new TrainB(interSection);
        trainB.setName("Train-B");
        trainA.start();
        trainB.start();
    }

    public static class TrainB extends Thread {
        private InterSection interSection;
        private Random random = new Random();

        public TrainB(InterSection interSection) {
            this.interSection = interSection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                interSection.takeRoadB();
            }
        }
    }

    public static class TrainA extends Thread {
        private InterSection interSection;
        private Random random = new Random();

        public TrainA(InterSection interSection) {
            this.interSection = interSection;
        }

        @Override
        public void run() {
            while (true) {
                long sleepTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                interSection.takeRoadA();
            }
        }
    }

    public static class InterSection {
        private final Object roadA = new Object();
        private final Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadB) {
                System.out.println("Road B is locked by thread " + Thread.currentThread().getName());
                synchronized (roadA) {
                    System.out.println("Train is passing through RoadA");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void takeRoadB() {
            synchronized (roadB) {
                System.out.println("Road B is locked by thread " + Thread.currentThread().getName());
                synchronized (roadA) {
                    System.out.println("Train is passing through RoadB");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
