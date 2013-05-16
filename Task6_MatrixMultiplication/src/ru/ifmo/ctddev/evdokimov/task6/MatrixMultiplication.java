package ru.ifmo.ctddev.evdokimov.task6;

import java.util.Arrays;
import java.util.Random;

/**
 * Class for matrix multiplication
 * 
 * @author Anton Evdokimov
 *
 */
public class MatrixMultiplication {
	/**
	 * Size of matrix
	 */
	private int n;
	
	/**
	 * Matrices
	 */
	private int[][] A, B, resultMatrix;
	
	/**
	 * Init random matrix and setup threads
	 * 
	 * @param n size of matrix
	 * @param m count of threads
	 * @throws InterruptedException throws if thread was interrupted
	 */
	public MatrixMultiplication(int n, int m) throws InterruptedException {
		Random random = new Random();
		
		this.n = n;
		
		A = new int[n][n];
		B = new int[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				A[i][j] = random.nextInt();
				B[i][j] = random.nextInt();
			}
		}
		
		resultMatrix = new int[n][n];
		
		int n2 = n * n;
		
		double dblocks = ((double) n2 / m);
		int blocks = (int) Math.ceil(dblocks);
		if (n2 % m == 0) {
			blocks = n2 / m;
		}
		
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
			threads[i].join();
		}
		
		int sum = 0;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sum += resultMatrix[i][j];
			}
		}
		System.out.println(sum);
	}
	
	/**
	 * Class calculate part of matrix
	 * 
	 * @author Anton Evdokimov
	 *
	 */
	private class Worker implements Runnable {
		/**
		 * Stored indexes
		 */
		private int[] indx;
		
		/**
		 * Init vars
		 * 
		 * @param indx array of indexes
		 * @param count count of indexes
		 */
		public Worker(int[] indx, int count) {
			this.indx = Arrays.copyOf(indx, count);
		}
		
		/**
		 * Calculate part of matrix
		 */
		@Override
		public void run() {
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


	/**
	 * @param args input parameters 
	 *     $1 -- size of matrix
	 *     $2 -- count of threads
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: MatrixMultiplication <size of matrix> <count of thread>");
			return;
		}
		int n = Integer.valueOf(args[0]);
		int m = Integer.valueOf(args[1]);
		
		double[] time = new double[m];
		final int COUNT_REPEAT = 4;
		
		for (int i = 1; i <= m; i++) {
			System.out.format("%d ", i);
			
			double sum = 0;
			
			for (int j = 0; j < COUNT_REPEAT; j++) {
				long start = System.nanoTime();
				try {
					new MatrixMultiplication(n, i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				long d = System.nanoTime() - start;
				
				sum += d;
			}
			
			sum /= COUNT_REPEAT;
			
			time[i - 1] = sum;
		}
		System.out.println();
		for (int i = 0; i < m; i++) {
			System.out.format("%f\n", time[i]);
		}
	}
}
