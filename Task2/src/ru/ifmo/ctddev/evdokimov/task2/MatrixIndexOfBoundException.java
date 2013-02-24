package ru.ifmo.ctddev.evdokimov.task2;

public class MatrixIndexOfBoundException extends RuntimeException {
	public MatrixIndexOfBoundException(String message) {
		super(message);
	}
	
	public MatrixIndexOfBoundException(int i, int j) {
		super(String.format("Wrong indexes: %d, %d", i, j));
	}
	
	
	
	public MatrixIndexOfBoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
