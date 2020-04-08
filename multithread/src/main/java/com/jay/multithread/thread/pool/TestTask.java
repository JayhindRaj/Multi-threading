package com.jay.multithread.thread.pool;

public class TestTask implements Runnable {

	private int taskId;
	
	public TestTask(int number) {
		this.taskId = number;
	}
	
	public void run() {
		try {
			System.out.println("Start executing task id " + this.taskId);
			Thread.sleep(500);
			System.out.println("End executing task id " + this.taskId);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
