package ru.ifmo.ctddev.evdokimov.task6;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		MatrixMultiplication mm2 = new MatrixMultiplication(1000, 5);
//		System.out.println("end");
//		int N = 10;
//		Thread[] t = new Thread[N];
//		
//		for (int i = 0; i < N; i++) {
//			Thread.sleep(3000);
//			t[i] = new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					System.out.println("run");
//					try {
//						Thread.sleep(10 * 1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				
//			});
//			
//			t[i].start();
//		}
//		
//		
//		for (int i = 0; i < N; i++) {
//			t[i].join();
//		}
//		System.out.println("end");
//		
//		
		int n = 100;
		int m = 16;
		
		double[] time = new double[m];
		final int COUNT_REPEAT = 1;
		
		
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
