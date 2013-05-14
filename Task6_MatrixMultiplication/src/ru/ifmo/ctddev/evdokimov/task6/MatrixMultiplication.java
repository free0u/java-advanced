package ru.ifmo.ctddev.evdokimov.task6;

import java.util.Arrays;
import java.util.Random;

public class MatrixMultiplication {
	private int n;
	private int[][] A, B, resultMatrix;
	
	/**
	 * Print matrix to stdout
	 * 
	 * @param matrix matrix to print
	 */
	private void dumpMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.format("%d ", matrix[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	private boolean checkResult(int[][] a, int[][] b, int[][] c) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int res = 0;
				for (int k = 0; k < n; k++) {
					res += a[i][k] * b[k][j];
				}
				
				if (res != c[i][j]) return false;
			}
		}
		
		return true;
	}
	
	
	/**
	 * 
	 * @param n size of matrix
	 * @param m count of threads
	 * @throws Exception 
	 */
	public MatrixMultiplication(int n, int m) throws Exception {
		Random random = new Random();
		
		this.n = n;
		
		A = new int[n][n];
		B = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = random.nextInt(10);
				B[i][j] = random.nextInt(10);
			}
		}
		
		resultMatrix = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				int res = 0;
				for (int k = 0; k < n; k++) {
					res += A[i][k] * B[k][j];
				}
				resultMatrix[i][j] = res;
			}
		}
		

		int n2 = n * n;
		
		double dblocks = ((double) n2 / m);
		int blocks = (int) Math.ceil(dblocks);
		if (n2 % m == 0) {
			blocks = n2 / m;
		}
		
		//int blocks = n2 / m;
		//if (n2 % m != 0) {
		//	blocks++;
		//}
		//System.out.println(blocks);
		
		Thread[] threads = new Thread[m];
		
		int[] indx = new int[n2];
		int pos = 0;
		int count;
		int indTread = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < blocks; j++) {
				indx[j] = pos++;
			}
			
			
			count = blocks;
			while (count > 0 && indx[count - 1] >= n2) {
				count--;
			}
			
			threads[indTread] = new Thread(new Worker(indx, count));
			threads[indTread].start();
			
			indTread++;
		}
		
		for (int i = 0; i < m; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
//		if (!checkResult(A, B, resultMatrix)) {
//			throw new Exception("Ans wrong");
//		}
	}
	
	private class Worker implements Runnable {
		int[] indx;
		public Worker(int[] indx, int count) {
			this.indx = Arrays.copyOf(indx, count);
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			for (int g = 0; g < indx.length; g++) {
				int i = indx[g] / n;
				int j = indx[g] % n;
				
				int res = 0;
				for (int k = 0; k < n; k++) {
					res += A[i][k] * B[k][j];
				}
				
				resultMatrix[i][j] = res;
			}
		}
		
	}
}
