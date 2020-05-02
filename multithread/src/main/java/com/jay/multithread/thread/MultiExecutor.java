package com.jay.multithread.thread;

import java.util.ArrayList;
import java.util.List;

public class MultiExecutor {

    // Add any necessary member variables here
    private List<Runnable> tasks;

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        // Complete your code here
        this.tasks = tasks;
    }

    public static void main(String[] args) {
        List<Runnable> tasks = new ArrayList<>();

        tasks.add(() -> System.out.println("Executing Task-1"));
        tasks.add(() -> System.out.println("Executing Task-2"));
        tasks.add(() -> System.out.println("Executing Task-3"));
        tasks.add(() -> System.out.println("Executing Task-4"));
        tasks.add(() -> System.out.println("Executing Task-5"));
        tasks.add(() -> System.out.println("Executing Task-6"));

        new MultiExecutor(tasks).executeAll();
    }

    /**
     * Starts and executes all the tasks concurrently
     */
    public void executeAll() {
        // complete your code here
        this.tasks.forEach(t -> new Thread(t).start());
    }
}
