package ru.ifmo.ctddev.evdokimov.task2;

public class Matrix {
	private int[][] data;
	private int w, h;

	public Matrix(int[][] data) {
		w = data.length;

		if (w > 0) {
			if (data[0] == null) {
				throw new MatrixWrongOperandException(String.format("Matrix contain null row: %d", 0));
			}
			h = data[0].length;
		} else {
			h = 0;
		}

		for (int i = 0; i < w; ++i) {
			if (data[i] == null) {
				throw new MatrixWrongOperandException(String.format("Matrix contain null row: %d", i));
			}
			if (h != data[i].length) {
				throw new MatrixWrongOperandException("Matrix is not rectangle");
			}
		}

		this.data = new int[w][h];
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	public Matrix(int a, int b) {
		w = a;
		h = b;
		data = new int[a][b];
	}
	
	public Matrix transpose() {
		Matrix res = new Matrix(h, w);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[j][i] = data[i][j]; 
			}
		}
		return res;
	}

	public Matrix add(Matrix a) {
		if (h != a.h || w != a.w) {
			throw new MatrixWrongOperandException(String.format("Different sizes of matrix: %dx%x and %dx%d", w, h, a.w, a.h));
		}

		Matrix res = new Matrix(data);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[i][j] += a.data[i][j];
			}
		}

		return res;
	}

	public Matrix sub(Matrix a) {
		if (h != a.h || w != a.w) {
			throw new MatrixWrongOperandException(String.format("Different sizes of matrix: %dx%x and %dx%d", w, h, a.w, a.h));
		}

		Matrix res = new Matrix(data);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[i][j] -= a.data[i][j];
			}
		}

		return res;
	}

	public Matrix mul(Matrix a) {
		if (h != a.w) {
			throw new MatrixWrongOperandException(String.format("Different sizes of matrix: %dx%x and %dx%d", w, h, a.w, a.h));
		}

		Matrix res = new Matrix(w, a.h);
		
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < a.h; ++j) {
				int acc = 0;
				for (int g = 0; g < h; ++g) {
					acc += data[i][g] * a.data[g][j];
				}
				res.data[i][j] = acc;
			}
		}
		
		return res;
	}

	public int get(int i, int j) {
		if (!(i < w && j < h && i >= 0 && j >= 0)) {
			throw new MatrixIndexOfBoundException(i, j);
		}
		return data[i][j];
	}

	public void set(int i, int j, int value) {
		if (!(i < w && j < h && i >= 0 && j >= 0)) {
			throw new MatrixIndexOfBoundException(i, j);
		}
		data[i][j] = value;
	}

	public Matrix scale(int m) {
		Matrix res = new Matrix(data);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[i][j] *= m;
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
		Matrix C = A.mul(A.transpose()).add(B);
		System.out.println(C);
	}
}
