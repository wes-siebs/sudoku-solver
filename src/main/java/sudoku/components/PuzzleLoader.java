package main.java.sudoku.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PuzzleLoader {
	
	public static int[][] loadPuzzle(String filename) {
		InputStream inputStream = null;
		
		try {
			File file = new File(filename);
			inputStream = new FileInputStream(file);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			
			String line = reader.readLine();
			
			if (line == null) {
				System.err.println("File cannot be empty: " + filename);
				reader.close();
				return null;
			}
			
			int unit = Integer.parseInt(line);
			int size = unit * unit;
			int[][] values = new int[size][size];
			
			for (int i = 0; i < size; i++) {
				String[] row = reader.readLine().split(" ");
				
				for (int j = 0; j < size; j++) {
					values[i][j] = Integer.parseInt(row[j]);
				}
			}
			
			reader.close();
			return values;
		} catch (NullPointerException exception) {
			System.err.println("Filename cannot be null");
		} catch (FileNotFoundException e) {
			System.err.println("No such file: " + filename);
		} catch (IOException e) {
			System.err.println("Error reading file: " + filename);
			e.printStackTrace();
		}
		
		return null;
	}

}
