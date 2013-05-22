/**
 * 
 */
package ru.ifmo.ctddev.evdokimov.task7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Multithreading realization of TaskRunner
 * 
 * @author Anton Evdokimov
 */
public class TaskRunnerImpl implements TaskRunner {
	/** Queue for input tasks */
	private BlockingQueue<TaskItem<?, ?>> taskQueue = new LinkedBlockingQueue<>();
	
	/**
	 * Task with addition content
	 * 
	 * @author Anton Evdokimov
	 *
	 * @param <X> - type of return value
	 * @param <Y> - type of arg
	 */
	private class TaskItem<X, Y> {
		/** store task */
		private Task<X, Y> task;
		/** store result */
		private X result;
		/** store input value */
		private Y value;
		/** true if task completed */
		private boolean haveResult;
		
		/**
		 * @param task input task
		 * @param value value for task
		 */
		public TaskItem(Task<X, Y> task, Y value) {
			this.task = task;
			this.value = value;
		}
		
		/**
		 * Execution of task
		 */
		public synchronized void exec() {
			result = task.run(value);
			haveResult = true;
			this.notifyAll();
		}
		
		/**
		 * @return result of task
		 */
		public X getResult() {
			return result;
		}
		
		/**
		 * @return true if task completed
		 */
		public boolean haveResult() {
			return haveResult;
		}
	}

	/**
	 * Class for multithread execution of tasks
	 * 
	 * @author Anton Evdokimov
	 */
	private class Worker implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					TaskItem<?, ?> ti = taskQueue.take();
					ti.exec();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	/**
	 * @param countThreads - count of threads
	 */
	public TaskRunnerImpl(int countThreads) {
		Thread[] th = new Thread[countThreads];
		for (int i = 0; i < countThreads; i++) {
			th[i] = new Thread(new Worker());
			th[i].start();
		}
	}
	
	@Override
	public <X, Y> X run(Task<X, Y> task, Y value) {
		TaskItem<X, Y> taskItem = new TaskItem<X, Y>(task, value);
		taskQueue.add(taskItem);
		
		synchronized (taskItem) {
			while (!taskItem.haveResult()) {
				try {
					taskItem.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		return taskItem.getResult();
	}
}
