package ru.ifmo.ctddev.evdokimov.task2;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws MatrixIOException {
//		if (args.length == 0) {
//			throw new IllegalArgumentException("need name of file");
//		}
//		
//		String fileName = args[0];
//		
		
		File file = new File("input.txt");
		Matrix A = new Matrix(file);
		System.out.println(A);
		
//		int[][] a = { 
//				{1, 2},
//				{3, 4}
//		};
//
//		int[][] b = { 
//				{5, 6},
//				{7, 8}
//		};
//
//		Matrix A = new Matrix(a);
//		Matrix B = new Matrix(b);
//		Matrix C = A.multiply(A.transpose()).add(B);
//		System.out.println(C);
	}

}
