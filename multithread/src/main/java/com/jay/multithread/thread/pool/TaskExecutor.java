package com.jay.multithread.thread.pool;

/**
 * TaskExecutor
 * 
 * @author jayrajpo
 *
 */
public class TaskExecutor implements Runnable {
	private BlockingQueue<Runnable> queue;

	public TaskExecutor(BlockingQueue<Runnable> queue) {
		this.queue = queue;
	}

	public void run() {

		try {
			while (true) {
				String threadName = Thread.currentThread().getName();
				Runnable task = queue.dequeue();
				System.out.println("Task started by thread " + threadName);
				task.run();
				System.out.println("Task finished by thread " + threadName);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
