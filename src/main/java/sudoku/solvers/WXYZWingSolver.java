package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class WXYZWingSolver extends Solver {

	@Override
	public String getName() {
		return "WXYZ-Wing";
	}

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = new Move();

		int[][] noteTuples = Utilities.nCkTuples(9, 4, 1);
		for (int[] tuple : noteTuples) {
			List<Cell> candidates = new ArrayList<>();
			int value = 0;
			for (int note : tuple) {
				value += 1 << note;
			}

			for (Cell[] row : board.rows) {
				for (Cell cell : row) {
					if (cell.value != 0) {
						continue;
					}
					int cellValue = cell.getIntNotes();
					if ((cellValue & value) <= value) {
						candidates.add(cell);
					}
				}
			}

			if (candidates.size() < tuple.length) {
				continue;
			}

			int[][] cellTuples = Utilities.nCkTuples(candidates.size(), tuple.length, 0);
			for (int[] cellTuple : cellTuples) {
				Cell[] cells = new Cell[tuple.length];
				for (int i = 0; i < tuple.length; i++) {
					cells[i] = candidates.get(cellTuple[i]);
				}

				if (!this.areValid(cells, value)) {
					continue;
				}

				int unrestricted = 0;
				boolean valid = false;
				for (int note : tuple) {
					if (!this.isRestricted(cells, note)) {
						if (unrestricted == 0) {
							unrestricted = note;
							valid = true;
						} else {
							valid = false;
							break;
						}
					}
				}

				if (valid) {
					for (Cell[] row : board.rows) {
						for (Cell cell : row) {
							boolean remove = true;
							for (Cell candidate : cells) {
								if (candidate == cell) {
									remove = false;
									break;
								}
								if (candidate.notes[unrestricted] && !candidate.canSee(cell)) {
									remove = false;
									break;
								}
							}

							if (remove) {
								nextMove.addChange(new NoteChange(cell, unrestricted));
							}
						}
					}

					if (!nextMove.isEmpty()) {
						return nextMove;
					}
				}

			}

		}

		return nextMove;
	}

	private boolean areValid(Cell[] cells, int value) {
		int cellValue = 0;
		for (Cell cell : cells) {
			cellValue |= cell.getIntNotes();
		}

		return cellValue == value;
	}

	private boolean isRestricted(Cell[] cells, int note) {
		List<Cell> candidates = new ArrayList<>();
		for (Cell cell : cells) {
			if (cell.notes[note]) {
				candidates.add(cell);
			}
		}

		for (Cell cell1 : candidates) {
			for (Cell cell2 : candidates) {
				if (!cell1.canSee(cell2)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int getDifficulty() {
		return 300;
	}

}
