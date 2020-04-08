package com.jay.multithread.runnable;

public class RunnableTest {
	public static void main(String[] args) {
		Thread t = new Thread(new MyRunnable());
		t.start();
	}
}
