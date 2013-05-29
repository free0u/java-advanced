package ru.ifmo.ctddev.evdokimov.task8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Multithreading realization of TaskRunner with ExecutorService
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
	
	@Override
	public <X, Y> X run(final Task<X, Y> task, final Y value) {
		Future<X> future = es.submit(new Callable<X>() {
			@Override
			public X call() {
				return task.run(value);
			}
		});
		
		X result = null;
		try {
			result = future.get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException ignore) {
			System.out.println("computation threw an exception");
		}
		
		return result;
	}
}
