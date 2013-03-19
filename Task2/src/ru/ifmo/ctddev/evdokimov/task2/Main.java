package ru.ifmo.ctddev.evdokimov.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		if (args.length < 2) {
			throw new IllegalArgumentException("Need names of files");
		}
		
		String fileNameInput = args[0];
		String fileNameOutput = args[1];
		
		try {
			Scanner sc = new Scanner(new File(fileNameInput));
			
			try {
				Matrix A = new Matrix(sc);
				Matrix B = new Matrix(sc);
				Matrix C = new Matrix(sc);
				
				Matrix res = A.multiply(A).add(B.multiply(C));
				
				res.write(new File(fileNameOutput));
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				sc.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

	}

}
