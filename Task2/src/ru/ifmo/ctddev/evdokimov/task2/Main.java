package ru.ifmo.ctddev.evdokimov.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException, MatrixIOException {
		if (args.length < 2) {
			throw new IllegalArgumentException("Need names of file");
		}
		
		String fileNameInput = args[0];
		String fileNameOutput = args[1];
		
		
		try {
			Matrix A = new Matrix();
			Matrix B = new Matrix();
			
			File file = new File(fileNameInput);
			Scanner sc = new Scanner(file);
			A.readMatrixWithScanner(sc);
			B.readMatrixWithScanner(sc);
			Matrix C = A.multiply(B);
			
			File fout = new File(fileNameOutput);
			C.write(fout);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MatrixWrongDataException e) {
			e.printStackTrace();
		} catch (MatrixIOException e) {
			e.printStackTrace();
		}
	}

}
