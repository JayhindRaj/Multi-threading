package com.jay.multithread.thread.pool;

/**
 * Custom ThreadPool
 * 
 * @author jayrajpo
 *
 */
public class ThreadPool {
	private BlockingQueue<Runnable> queue;

	public ThreadPool(int queueSize, int nThread) {
		this.queue = new BlockingQueue<Runnable>(queueSize);
		String threadName = null;
		TaskExecutor executor = new TaskExecutor(this.queue);
		for (int count = 0; count < nThread; count++) {
			threadName = "Thread-" + count;
			Thread thread = new Thread(executor, threadName);
			thread.start();
		}
	}
	
	public void submitTask(Runnable task) throws InterruptedException {
		this.queue.enqueue(task);
	}
 }
