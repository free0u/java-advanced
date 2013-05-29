package ru.ifmo.ctddev.evdokimov.task8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Multithreading realization of TaskRunner
 * 
 * @author Anton Evdokimov
 */
public class TaskRunnerImpl implements TaskRunner {
	/** Executor service for input tasks */
	private ExecutorService es;
	
	/**
	 * @param countThreads - count of threads
	 */
	public TaskRunnerImpl(int countThreads) {
		es = Executors.newFixedThreadPool(countThreads);
	}
	
	private class CallableTask<X, Y> implements Callable<X> {
		private Task<X, Y> task;
		private Y value;

		public CallableTask(Task<X, Y> task, Y value) {
			this.task = task;
			this.value = value;
		}
		
		@Override
		public X call() {
			return task.run(value);
		}
	}
	
	@Override
	public <X, Y> X run(Task<X, Y> task, Y value) {
		Future<X> future = es.submit(new CallableTask<X, Y>(task, value));
		while (!future.isDone());
		
		X result = null;
		try {
			result = future.get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException ignore) {
		}
		
		return result;
	}
}
