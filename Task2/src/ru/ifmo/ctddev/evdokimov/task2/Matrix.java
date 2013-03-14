package ru.ifmo.ctddev.evdokimov.task2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
				throw new MatrixWrongOperandException(String.format("Matrix is not rectangle. Different size in %d row", i));
			}
		}

		this.data = new int[w][h];
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	
	public Matrix(File file) throws MatrixIOException {
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			Scanner sc = new Scanner(reader);
			
			readMatrix(sc);
			
			reader.close();
		} catch (FileNotFoundException e) {
			throw new MatrixFileNotFoundException("File " + file.getName() + " not found", e);
		} catch (MatrixIOException e) {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ignore) { }
			
			throw e;
		} catch (IOException e) { 
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ignore) {}
			
			throw new MatrixIOException(e.getMessage(), e);
		}
	}
	
	
	public void readMatrix(Scanner sc) throws MatrixWrongDataException {
		if (sc.hasNextInt()) {
			w = sc.nextInt();
			if (w < 0) {
				throw new MatrixWrongDataException("Width should be non-negative");
			}
		} else
		{
			throw new MatrixWrongDataException("Not found width in file");
			
		}
		
		if (sc.hasNextInt()) {
			h = sc.nextInt();
			if (h < 0) {
				throw new MatrixWrongDataException("Height should be non-negative");
			}
		} else
		{
			throw new MatrixWrongDataException("Not found height in file");
		}
		
		data = new int[w][h];
		
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				if (sc.hasNextInt()) {
					data[i][j] = sc.nextInt();
				} else
				{
					throw new MatrixWrongDataException("Not found next int in file");
				}
			}
		}
	}
	
	public Matrix(int a, int b) {
		w = a;
		h = b;
		data = new int[a][b];
	}
	
	public Matrix() {
		this(0, 0);
	}
	
	boolean write(File file) throws MatrixIOException {
		PrintWriter writer = null;
		boolean res = true;
		try {
			writer = new PrintWriter(file);

			writer.println(w + " " + h);
			for (int i = 0; i < w; ++i) {
				for (int j = 0; j < h; ++j) {
					writer.print(data[i][j]);
					writer.print(" ");
					
				}
				writer.println();
			}
			
			if (writer.checkError()) {
				res = false;
			}
			
			writer.close();
		} 
		catch (FileNotFoundException e) {
			throw new MatrixFileNotFoundException("File " + file.getName() + " not found", e);
		}
		
		return res;
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

	public Matrix substract(Matrix a) {
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

	public Matrix multiply(Matrix a) {
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
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < w; ++i) {
			for (int j = 0; j < h; ++j) {
				res.append(data[i][j]);
				res.append(" ");
			}
			res.append("\n");
		}
		return res.toString();
	}
}
