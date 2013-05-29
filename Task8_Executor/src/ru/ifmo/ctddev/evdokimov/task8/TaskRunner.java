package ru.ifmo.ctddev.evdokimov.task8;

/**
 * Interface of TaskRunner
 * 
 * @author Anton Evdokimov
 */
public interface TaskRunner {
	/**
	 * Execute task
	 * 
	 * @param task task for execution
	 * @param value input value for task
	 * @return output value from task
	 */
	<X, Y> X run(Task<X, Y> task, Y value);
}
