package edu.bsu.jlmiller.MatrixMultiplier;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MatrixMultiplier {

	public static int[][] matrix1;
	public static int[][] matrix2;
	public static int[][] outputMatrix;

	public static void main(String[] args) {
		List<MultiplyThread> threadList = new ArrayList<MultiplyThread>();
		parseFile("test2");
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix2[0].length; j++) {
				threadList.add(new MultiplyThread(i, j));
			}
		}
		for (MultiplyThread thread : threadList) {
			thread.start();
		}
		for (MultiplyThread thread : threadList) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
			for (int i = 0; i < matrix1.length; i++) {
				for (int j = 0; j < matrix2[0].length; j++) {
					System.out.print(outputMatrix[i][j] + " ");
					writer.print(outputMatrix[i][j] + " ");
				}
				writer.println();
				System.out.println();
			}
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static void parseFile(String filePath) {
		try {
			int rowNumber1 = 0;
			int rowNumber2 = 0;
			int colNumber1 = 0;
			int colNumber2 = 0;
			boolean isRow = false;
			boolean starsPassed = false;
			BufferedReader dimensionReader = new BufferedReader(new FileReader(filePath));
			String line;
			while ((line = dimensionReader.readLine()) != null) {
				int colCounter = 0;
				if (line.contains("*")) {
					starsPassed = true;
					continue;
				}
				String[] numbers = line.split("\\s+");
				for (String number : numbers) {
					colCounter++;
					isRow = true;
				}
				if (isRow) {
					if (starsPassed) {
						colNumber2 = colCounter;
						rowNumber2++;
					} else {
						colNumber1 = colCounter;
						rowNumber1++;
					}
					isRow = false;
				}
			}
			matrix1 = new int[rowNumber1][colNumber1];
			matrix2 = new int[rowNumber2][colNumber2];
			outputMatrix = new int[rowNumber1][colNumber2];
			dimensionReader.close();
			starsPassed = false;
			rowNumber1 = 0;
			rowNumber2 = 0;
			colNumber1 = 0;
			colNumber2 = 0;
			BufferedReader populator = new BufferedReader(new FileReader(filePath));
			while ((line  = populator.readLine()) != null) {
				if (line.contains("*")) {
					starsPassed = true;
					continue;
				}
				String[] numbers = line.split("\\s+");
				for (String number : numbers) {
					if (starsPassed) {
						matrix2[rowNumber2][colNumber2] = Integer.parseInt(number);
						colNumber2++;
					} else {
						matrix1[rowNumber1][colNumber1] = Integer.parseInt(number);
						colNumber1++;
					}
					isRow = true;
				}
				if (isRow) {
					if (starsPassed) {
						rowNumber2++;
						colNumber2 = 0;
					} else {
						rowNumber1++;
						colNumber1 = 0;
					}
					isRow = false;
				}
			}
			populator.close();
		} catch (IOException e) {
			System.out.println("File not found!");
			System.exit(0);
			e.printStackTrace();
		}
	}

}
