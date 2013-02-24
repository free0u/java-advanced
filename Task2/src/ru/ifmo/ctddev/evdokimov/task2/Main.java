package ru.ifmo.ctddev.evdokimov.task2;

public class Main {
	public static void main(String[] args) {
		int[][] a = { 
				{1, 2},
				{3, 4}
		};

		int[][] b = { 
				{5, 6},
				{7, 8}
		};

		Matrix A = new Matrix(a);
		Matrix B = new Matrix(b);
		Matrix C = A.multiply(A.transpose()).add(B);
		System.out.println(C);
	}

}
