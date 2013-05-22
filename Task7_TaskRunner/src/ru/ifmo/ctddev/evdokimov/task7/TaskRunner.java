package ru.ifmo.ctddev.evdokimov.task7;

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
