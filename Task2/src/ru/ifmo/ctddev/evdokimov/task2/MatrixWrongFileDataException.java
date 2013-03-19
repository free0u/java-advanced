package ru.ifmo.ctddev.evdokimov.task2;

public class MatrixWrongFileDataException extends RuntimeException {
	public MatrixWrongFileDataException(String message) {
		super(message);
	}

	public MatrixWrongFileDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
