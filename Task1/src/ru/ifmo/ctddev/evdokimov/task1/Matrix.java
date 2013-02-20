package ru.ifmo.ctddev.evdokimov.task1;

public class Matrix {
	private int[][] data = null;
	private int w, h;

	public Matrix(int[][] data) throws MatrixWrongOperandException {
		w = data.length;

		if (w > 0) {
			h = data[0].length;
		} else {
			h = 0;
		}

		for (int i = 0; i < w; ++i) {
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
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				data[i][j] = 0;
			}
		}
	}

	public Matrix add(Matrix a) throws MatrixWrongOperandException {
		if (h != a.h || w != a.w) {
			throw new MatrixWrongOperandException("Wrong operand in add");
		}

		Matrix res = new Matrix(data);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[i][j] += a.data[i][j];
			}
		}

		return res;
	}

	public Matrix sub(Matrix a) throws MatrixWrongOperandException {
		if (h != a.h || w != a.w) {
			throw new MatrixWrongOperandException("Wrong operand in sub");
		}

		Matrix res = new Matrix(data);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[i][j] -= a.data[i][j];
			}
		}

		return res;
	}

	// TODO change mul
	public Matrix mul(Matrix a) throws MatrixWrongOperandException {
		if (h != a.h || w != a.w) {
			throw new IllegalArgumentException("Wrong op in mul");
		}

		Matrix res = new Matrix(data);
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.data[i][j] -= a.data[i][j];
			}
		}

		return res;
	}

	public int get(int i, int j) throws MatrixIndexOfBoundException {
		if (!(i < w && j < h)) {
			throw new MatrixIndexOfBoundException("Wrong indexes in get");
		}
		return data[i][j];
	}

	public void set(int i, int j, int value) throws MatrixWrongOperandException {
		if (!(i < w && j < h)) {
			throw new MatrixWrongOperandException("Wrong indexes in set");
		}
		data[i][j] = value;
	}

	public Matrix scale(int m) throws MatrixWrongOperandException {
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

	public static void main(String[] args) throws MatrixWrongOperandException {
		int[][] a = { { 1, 2 }, { 3, 4 } };

		int[][] b = { { 5, 6 }, { 7, 8 } };

		Matrix A = new Matrix(a);
		Matrix B = new Matrix(b);

		Matrix C = A.sub(B).add(B).sub(B).add(B);
		System.out.println(C);
	}

}
