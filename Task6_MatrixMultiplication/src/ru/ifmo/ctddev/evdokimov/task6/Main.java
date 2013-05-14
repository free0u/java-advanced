package ru.ifmo.ctddev.evdokimov.task6;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//MatrixMultiplication mm2 = new MatrixMultiplication(4, 5);
		
		int n = 400;
		int m = 16;
		
		double[] time = new double[m];
		final int COUNT_REPEAT = 5;
		
		
		for (int i = 1; i <= m; i++) {
			System.out.format("%d ", i);
			
			double sum = 0;
			
			for (int j = 0; j < COUNT_REPEAT; j++) {
				long start = System.nanoTime();
				new MatrixMultiplication(n, i);
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
