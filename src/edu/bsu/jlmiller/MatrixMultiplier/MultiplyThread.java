package edu.bsu.jlmiller.MatrixMultiplier;

public class MultiplyThread extends Thread {
	
	int rowNumber;
	int colNumber;

	public MultiplyThread(int rowNumber, int colNumber) {
		super();
		this.rowNumber = rowNumber;
		this.colNumber = colNumber;
	}
	
	@Override
	public void run() {
		int output = 0;
		for (int i = 0; i < MatrixMultiplier.matrix1[rowNumber].length; i++) {
			output += MatrixMultiplier.matrix1[rowNumber][i] * MatrixMultiplier.matrix2[i][colNumber];
		}
		MatrixMultiplier.outputMatrix[rowNumber][colNumber] = output;
	}
	
}
