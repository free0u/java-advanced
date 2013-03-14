package ru.ifmo.ctddev.evdokimov.task2;

public class MatrixWrongDataException extends Exception {

	public MatrixWrongDataException(String message) {
		super(message);
	}

	public MatrixWrongDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
