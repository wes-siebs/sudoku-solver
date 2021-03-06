package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class SimpleColoringSolver extends Solver {

	@Override
	public String getName() {
		return "Simple Coloring";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		for (int i = 1; i <= 9 && move.isEmpty(); i++) {
			this.checkNotes(board, i, move, this.getChains(board, i));
		}
	}

	private void checkNotes(Board board, int note, Move move, List<Cell[]> chains) {
		List<Cell> redCells = new ArrayList<>();
		List<Cell> blueCells = new ArrayList<>();

		if (chains.isEmpty()) {
			return;
		}

		redCells.add(chains.get(0)[0]);
		boolean madeChain = true;
		while (madeChain) {
			madeChain = false;

			for (int i = 0; i < chains.size(); i++) {

				Cell[] chain = chains.get(i);

				List<Cell> list = null;
				Cell toAdd = null;

				if (redCells.contains(chain[0])) {
					list = blueCells;
					toAdd = chain[1];
				} else if (blueCells.contains(chain[0])) {
					list = redCells;
					toAdd = chain[1];
				} else if (redCells.contains(chain[1])) {
					list = blueCells;
					toAdd = chain[0];
				} else if (blueCells.contains(chain[1])) {
					list = redCells;
					toAdd = chain[0];
				} else {
					continue;
				}

				if (!list.contains(toAdd)) {
					list.add(toAdd);
				}

				chains.remove(i--);
				madeChain = true;
			}
		}

		String desc = this.getName() + " on " + note + ":";
		desc += "\n\tRed Cells: ";
		for (Cell cell : redCells) {
			desc += cell.coordString();
		}
		desc += "\n\tBlue Cells: ";
		for (Cell cell : blueCells) {
			desc += cell.coordString();
		}
		
		if (this.violatesRule2(redCells)) {
			for (Cell cell : redCells) {
				move.addChange(new NoteChange(cell, note));
			}
			desc += "\n\tViolation of Rule 2: Removing " + note + " from all red cells";
		} else if (this.violatesRule2(blueCells)) {
			for (Cell cell : blueCells) {
				move.addChange(new NoteChange(cell, note));
			}
			desc += "\n\tViolation of Rule 2: Removing " + note + " from all blue cells";
		} else {
			desc += "\n\tViolation of Rule 4: Removing " + note + " from ";
			for (Cell[] row : board.rows) {
				for (Cell cell : row) {
					boolean seesRed = false;
					boolean seesBlue = false;

					for (Cell red : redCells) {
						if (cell.canSee(red)) {
							seesRed = true;
							break;
						}
					}
					for (Cell blue : blueCells) {
						if (cell.canSee(blue)) {
							seesBlue = true;
							break;
						}
					}

					if (seesRed && seesBlue) {
						move.addChange(new NoteChange(cell, note));
						if (cell.getNote(note)) {
							desc += cell.coordString();
						}
					}
				}
			}
		}
		
		if (!move.isEmpty()) {
			move.description = desc;
		} else if (!chains.isEmpty()) {
			checkNotes(board, note, move, chains);
		}
	}

	private List<Cell[]> getChains(Board board, int note) {
		List<Cell[]> chainList = new ArrayList<>();

		getChainsHelper(chainList, board.rows, note);
		getChainsHelper(chainList, board.columns, note);
		getChainsHelper(chainList, board.boxes, note);

		return chainList;
	}

	private void getChainsHelper(List<Cell[]> chains, Cell[][] houses, int note) {
		for (Cell[] house : houses) {
			Cell[] chain = new Cell[2];
			boolean valid = false;
			for (Cell cell : house) {
				if (cell.getNote(note)) {
					if (chain[0] == null) {
						chain[0] = cell;
					} else if (chain[1] == null) {
						chain[1] = cell;
						valid = true;
					} else {
						valid = false;
						break;
					}
				}
			}
			for (Cell[] otherChain : chains) {
				if (otherChain[0] == chain[0] && otherChain[1] == chain[1]) {
					valid = false;
				}
			}
			if (valid) {
				chains.add(chain);
			}
		}
	}

	private boolean violatesRule2(List<Cell> cells) {
		for (int i = 0; i < cells.size(); i++) {
			for (int j = i + 1; j < cells.size(); j++) {
				if (cells.get(i).canSee(cells.get(j))) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public int getDifficulty() {
		return 60;
	}

}
