package ru.ifmo.ctddev.evdokimov.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("Need names of file");
		}
		
		String fileNameInput = args[0];
		String fileNameOutput = args[1];
		
		
		try {
			Matrix A = new Matrix();
			Matrix B = new Matrix();
			Matrix C = new Matrix();
			
			File file = new File(fileNameInput);
			Scanner sc = new Scanner(file);
			
			A.readMatrix(sc);
			B.readMatrix(sc);
			C.readMatrix(sc);
			
			// A^2 + B * C
			Matrix res = A.multiply(A).add(B.multiply(C));
			
			File fout = new File(fileNameOutput);
			res.write(fout);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MatrixWrongDataException e) {
			e.printStackTrace();
		} catch (MatrixIOException e) {
			e.printStackTrace();
		}
	}

}
