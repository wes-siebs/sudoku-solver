package main.java.sudoku.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.Utilities;
import main.java.sudoku.variants.Variant;

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
				String[] row = reader.readLine().replace('_', '0').split(" ");

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
							cell.setNote(0, true);
							for (char c : row[j].toCharArray()) {
								int note = Integer.parseInt("" + c);
								cell.setNote(note, true);
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
						if (!cell.getNote(0)) {
							for (int i = 1; i < 10; i++) {
								cell.setNote(i, false);
							}
						}
						cell.setNote(0, false);
					}
				}
			} else {
				for (Cell[] row : rows) {
					for (Cell checkedCell : row) {
						for (Cell changedCell : row) {
							changedCell.setNote(checkedCell.getValue(), false);
						}
					}
				}

				for (Cell[] column : columns) {
					for (Cell checkedCell : column) {
						for (Cell changedCell : column) {
							changedCell.setNote(checkedCell.getValue(), false);
						}
					}
				}

				for (Cell[] box : boxes) {
					for (Cell checkedCell : box) {
						for (Cell changedCell : box) {
							changedCell.setNote(checkedCell.getValue(), false);
						}
					}
				}
			}

			Board board = new Board(rows, columns, boxes);

			while ((line = reader.readLine()) != null) {
				boolean read = false;
				for (Variant variant : Variant.variantList) {
					if (variant.getName().equals(line)) {
						read = true;
						variant.loadSolvers();

						List<String> lines = new ArrayList<>();
						while ((line = reader.readLine()) != null) {
							if (line.equals("end")) {
								break;
							} else {
								lines.add(line);
							}
						}

						board = variant.loadBoard(board, lines);
						break;
					}
				}

				if (!read) {
					System.out.println("Unrecognized variant: " + line);
					break;
				}
			}

			reader.close();

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
