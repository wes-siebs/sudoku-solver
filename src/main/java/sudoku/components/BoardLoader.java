package main.java.sudoku.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.java.sudoku.Utilities;

public class BoardLoader {

	public static Board loadBoard(String filename) {
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
			Cell[][] rows = new Cell[size][size];
			Cell[][] columns = new Cell[size][size];
			Cell[][] boxes = new Cell[size][size];
			boolean adv = false;

			for (int i = 0; i < size; i++) {
				String[] row = reader.readLine().split(" ");

				if (row.length == 1) {
					for (int j = 0; j < size; j++) {
						int value = Integer.parseInt("" + row[0].charAt(j));
						Cell cell = new Cell(value, i, j, Utilities.getBox(i, j, unit));
						rows[i][j] = cell;
						columns[j][i] = cell;
						boxes[cell.box][Utilities.getBoxPos(i, j, unit)] = cell;
					}
				} else {
					for (int j = 0; j < size; j++) {
						Cell cell = null;
						if (unit == 3 && row[j].length() > 1) {
							adv = true;
							cell = new Cell(0, i, j, Utilities.getBox(i, j, unit), false);
							cell.notes[0] = true;
							for (char c : row[j].toCharArray()) {
								int note = Integer.parseInt("" + c);
								cell.notes[note] = true;
							}
						} else {
							int value = Integer.parseInt(row[j]);
							cell = new Cell(value, i, j, Utilities.getBox(i, j, unit));
						}

						rows[i][j] = cell;
						columns[j][i] = cell;
						boxes[cell.box][Utilities.getBoxPos(i, j, unit)] = cell;
					}
				}

			}

			if (adv) {
				for (Cell[] row : rows) {
					for (Cell cell : row) {
						if (!cell.notes[0]) {
							for (int i = 1; i < cell.notes.length; i++) {
								cell.notes[i] = false;
							}
						}
						cell.notes[0] = false;
					}
				}
			} else {
				for (Cell[] row : rows) {
					for (Cell checkedCell : row) {
						for (Cell changedCell : row) {
							changedCell.notes[checkedCell.value] = false;
						}
					}
				}

				for (Cell[] column : columns) {
					for (Cell checkedCell : column) {
						for (Cell changedCell : column) {
							changedCell.notes[checkedCell.value] = false;
						}
					}
				}

				for (Cell[] box : boxes) {
					for (Cell checkedCell : box) {
						for (Cell changedCell : box) {
							changedCell.notes[checkedCell.value] = false;
						}
					}
				}
			}

			reader.close();

			Board board = new Board(rows, columns, boxes);
			return board;
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
