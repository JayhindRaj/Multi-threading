package com.jay.multithread.thread;

public class ThreadBasicConcepts {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            System.out.println("We are now in thread " + Thread.currentThread().getName());
            System.out.println("Current Thread Priority: " + Thread.currentThread().getPriority());
            throw new RuntimeException("Intentional Exception");
        });

        thread.setName("Misbehaving Thread");
        thread.setUncaughtExceptionHandler((Thread t, Throwable e) ->
                System.out.println("A critical error happened in thread " + t.getName() + " the error is " + e.getMessage()));

        thread.setPriority(Thread.MAX_PRIORITY);
        System.out.println("We are in thread " + Thread.currentThread().getName() + " before starting a new thread");
        thread.start();
        System.out.println("We are in thread " + Thread.currentThread().getName() + " after starting a new thread");

        Thread.sleep(1000);
    }
}
