package com.jay.multithread.thread.pool;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Generic Blocking Queue
 * 
 * @author jayrajpo
 *
 * @param <T>
 */
public class BlockingQueue<T> {

	private Queue<T> queue = new LinkedList<T>();
	private int EMPTY = 0;
	private int MAX_TASK_IN_QUEUE = -1;

	public BlockingQueue(int size) {
		this.MAX_TASK_IN_QUEUE = size;
	}

	public synchronized void enqueue(T task) throws InterruptedException {
		while (this.queue.size() == this.MAX_TASK_IN_QUEUE) {
			wait();
		}

		if (this.queue.size() == this.EMPTY) {
			notifyAll();
		}

		this.queue.offer(task);
	}

	public synchronized T dequeue() throws InterruptedException {

		while (this.queue.size() == this.EMPTY) {
			wait();
		}

		if (this.queue.size() == this.MAX_TASK_IN_QUEUE) {
			notifyAll();
		}

		return this.queue.poll();
	}
}
