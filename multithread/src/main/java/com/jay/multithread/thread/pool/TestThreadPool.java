package com.jay.multithread.thread.pool;

/**
 * 
 * @author jayrajpo
 *
 */
public class TestThreadPool {

	public static void main(String[] args) throws InterruptedException {
		// queueSize = 3
		// No. of threads = 4
		ThreadPool threadPool = new ThreadPool(3, 4);
		
		for(int i = 1; i <=10; i++) {
			TestTask task = new TestTask(i);
			threadPool.submitTask(task);
		}
	}
}

