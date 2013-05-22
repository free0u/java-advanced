package ru.ifmo.ctddev.evdokimov.task7;

/**
 * Interface for Task
 * 
 * @author Anton Evdokimov
 *
 * @param <X> - type of return value
 * @param <Y> - type of argument
 */
public interface Task<X, Y> {
	/**
	 * @param value input value
	 * @return output value
	 */
	X run(Y value);
}
