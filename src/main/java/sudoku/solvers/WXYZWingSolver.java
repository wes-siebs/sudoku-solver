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
	protected void makeNextMove(Move move, Board board) {
		int[][] noteTuples = Utilities.nCkTuples(9, 4, 1);
		for (int[] tuple : noteTuples) {
			List<Cell> candidates = new ArrayList<>();
			int value = 0;
			for (int note : tuple) {
				value += 1 << note;
			}

			for (Cell[] row : board.rows) {
				for (Cell cell : row) {
					if (cell.getValue() != 0) {
						continue;
					}
					int cellValue = cell.getNoteValue();
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
								if (candidate.getNote(unrestricted) && !(candidate.canSee(cell) || candidate == cell)) {
									remove = false;
									break;
								}
							}

							if (remove) {
								move.addChange(new NoteChange(cell, unrestricted));
							}
						}
					}

					if (!move.isEmpty()) {
						String desc = this.getName() + " on ";
						for (int note : tuple) {
							desc += note;
						}
						desc += " at ";
						for (Cell cell : cells) {
							desc += cell.coordString();
						}
						move.description = desc;
						return;
					}
				}

			}

		}
	}
	
	private boolean areValid(Cell[] cells, int value) {
		int cellValue = 0;
		for (Cell cell : cells) {
			cellValue |= cell.getNoteValue();
		}

		return cellValue == value;
	}

	private boolean isRestricted(Cell[] cells, int note) {
		List<Cell> candidates = new ArrayList<>();
		for (Cell cell : cells) {
			if (cell.getNote(note)) {
				candidates.add(cell);
			}
		}
		
		for (int i = 0; i < candidates.size(); i++) {
			for (int j = i + 1; j < candidates.size(); j++) {
				if (!candidates.get(i).canSee(candidates.get(j))) {
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
