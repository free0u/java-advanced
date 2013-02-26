package ru.ifmo.ctddev.evdokimov.task2;

public class MatrixWrongDataException extends MatrixIOException {

	public MatrixWrongDataException(String message) {
		super(message);
	}

	public MatrixWrongDataException(String message, Throwable cause) {
		super(message, cause);
	}

}
