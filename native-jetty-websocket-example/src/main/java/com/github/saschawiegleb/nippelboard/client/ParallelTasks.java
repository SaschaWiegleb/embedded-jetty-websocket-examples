package com.github.saschawiegleb.nippelboard.client;

import javaslang.control.Try;

public abstract class ParallelTasks<T> {

	public abstract Try<T> runTask();

	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runTask();
				} catch (Exception e) {
					System.err.println("Error on executing parallel task: " + e.getMessage());
				}
			}
		}).start();
	}
}
