package ru.ifmo.ctddev.evdokimov.task2;

public class MatrixWrongOperandException extends RuntimeException {
	public MatrixWrongOperandException(String message) {
		super(message);
	}
	
	public MatrixWrongOperandException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
