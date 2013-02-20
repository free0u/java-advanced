package ru.ifmo.ctddev.evdokimov.task1;

public class MatrixWrongOperandException extends Exception {
	public MatrixWrongOperandException(String message) {
		super(message);
	}
	
	public MatrixWrongOperandException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
