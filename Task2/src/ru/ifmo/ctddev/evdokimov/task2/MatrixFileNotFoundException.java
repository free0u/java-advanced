package ru.ifmo.ctddev.evdokimov.task2;

public class MatrixFileNotFoundException extends MatrixIOException {

	public MatrixFileNotFoundException(String message) {
		super(message);
	}

	public MatrixFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

}
