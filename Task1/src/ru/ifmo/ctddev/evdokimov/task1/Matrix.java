package ru.ifmo.ctddev.evdokimov.task1;

public class Matrix {
	private int[][] data = null;
	private int w, h;
	
	public Matrix(int[][] data) {
		w = data.length;
		
		if (w > 0) {
			h = data[0].length;
		} else
		{
			h = 0;
		}
		
		for (int i = 0; i < w; ++i) {
			if (h != data[i].length) {
				throw new IllegalArgumentException("Matrix non rectangle");
			}
			// TODO change
		}
		
		this.data = new int[w][h];
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				this.data[i][j] = data[i][j];
			}
		}
	}
	
	public Matrix add(Matrix a) {
		assert(w == a.w);
		assert(h == a.h);
		
		Matrix res = new Matrix(data);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[i][j] += a.data[i][j];
			}
		}
		
		return res;
	}
	
	public Matrix sub(Matrix a) {
		assert(w == a.w);
		assert(h == a.h);
		
		Matrix res = new Matrix(data);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[i][j] -= a.data[i][j];
			}
		}
		
		return res;
	}
	
	
	
	
	public String toString() {
		String res = "";
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res += data[i][j] + " ";
			}
			res += "\n";
		}
		return res;
	}
	
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
		
		Matrix C = A.sub(B);
		System.out.println(C);
	}

}
