package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class XYZWingSolver extends Solver {

	@Override
	public String getName() {
		return "XYZ-Wing";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		List<Cell> keys = this.getKeys(board);

		for (Cell key : keys) {
			List<Cell> pincers = this.getPincers(board, key);

			if (pincers.size() < 2) {
				continue;
			}

			int keyValue = key.getNoteValue();
			for (int i = 0; i < pincers.size(); i++) {
				int p1Value = pincers.get(i).getNoteValue();
				for (int j = i + 1; j < pincers.size(); j++) {
					int p2Value = pincers.get(j).getNoteValue();

					if ((p1Value | p2Value) == keyValue) {
						int toRemove = Utilities.unfold(p1Value & p2Value);

						for (Cell[] row : board.rows) {
							for (Cell cell : row) {
								if (cell.canSee(pincers.get(i))) {
									if (cell.canSee(pincers.get(j))) {
										if (cell.canSee(key)) {
											move.addChange(new NoteChange(cell, toRemove));
										}
									}
								}
							}
						}

						if (!move.isEmpty()) {
							String desc = this.getName() + " on ";
							for (int note = 1; note <= 9; note++) {
								if (key.getNote(note)) {
									desc += note;
								}
							}
							desc += "\n\tKey: " + key.coordString();
							desc += "\n\tPincers: " + pincers.get(i).coordString() + pincers.get(j).coordString();	
							move.description = desc;
							return;
						}
					}
				}
			}
		};
	}

	private List<Cell> getKeys(Board board) {
		List<Cell> keys = new ArrayList<>();

		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				if (cell.getNumNotes() == 3) {
					keys.add(cell);
				}
			}
		}

		return keys;
	}

	private List<Cell> getPincers(Board board, Cell key) {
		List<Cell> pincers = new ArrayList<>();
		int keyValue = key.getNoteValue();

		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				if (cell.getNumNotes() == 2) {
					if (key.canSee(cell)) {
						if ((keyValue | cell.getNoteValue()) == keyValue) {
							pincers.add(cell);
						}
					}
				}
			}
		}

		return pincers;
	}

	@Override
	public int getDifficulty() {
		return 100;
	}

}
