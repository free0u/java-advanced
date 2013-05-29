package ru.ifmo.ctddev.evdokimov.task8;


/**
 * 
 * Example of Task
 * 
 * @author Anton Evdokimov
 *
 * @param <X> - type of return value
 * @param <Y> - type of arg
 */
public class TaskRefl<X, Y extends X> implements Task<X, Y> {
	/**
	 * method return input value
	 * 
	 * @return input value
	 */
	@Override
	public X run(Y value) {
		return value;
	}
}
