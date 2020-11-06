package main.java.sudoku.solvers;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class UniqueRectangleSolver extends Solver {

	@Override
	public String getName() {
		return "Unique Rectangle";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		int[][] tuples = Utilities.nCkTuples(9, 2, 0);
		for (int[] rows : tuples) {
			for (int[] columns : tuples) {
				Cell[] cells = new Cell[4];
				int[] notes = new int[4];
				cells[0] = board.rows[rows[0]][columns[0]];
				cells[1] = board.rows[rows[0]][columns[1]];
				cells[2] = board.rows[rows[1]][columns[0]];
				cells[3] = board.rows[rows[1]][columns[1]];

				if (cells[0].box != cells[1].box && cells[0].box != cells[2].box) {
					continue;
				}

				int twoCount = 0;
				boolean valid = true;
				for (int i = 0; i < 4; i++) {
					notes[i] = cells[i].getIntNotes();
					if (cells[i].value != 0) {
						valid = false;
						break;
					} else if (cells[i].getNumNotes() == 2) {
						twoCount++;
					}
				}
				if (valid && twoCount >= 3) {
					Cell good = cells[0];
					Cell bad = cells[3];
					if (notes[0] == notes[1] && notes[1] == notes[2]) {
						subtractNotes(cells[3], cells[0], move);
					} else if (notes[0] == notes[1] && notes[1] == notes[3]) {
						subtractNotes(cells[2], cells[0], move);
						good = cells[0];
						bad = cells[2];
					} else if (notes[0] == notes[2] && notes[2] == notes[3]) {
						subtractNotes(cells[1], cells[0], move);
						good = cells[0];
						bad = cells[1];
					} else if (notes[1] == notes[2] && notes[2] == notes[3]) {
						subtractNotes(cells[0], cells[1], move);
						good = cells[1];
						bad = cells[0];
					}

					if (!move.isEmpty()) {
						String desc = this.getName() + " on ";
						for (int note = 1; note < good.notes.length; note++) {
							if (good.notes[note]) {
								desc += note;
							}
						}
						desc += " at ";
						for (Cell cell : cells) {
							desc += cell.coordString();
						}
						desc += " (remove from " + bad.coordString() + ")";
						move.description = desc;
						return;
					}
				}
			}
		}
	}

	private void subtractNotes(Cell a, Cell b, Move move) {
		for (int i = 0; i < b.notes.length; i++) {
			if (b.notes[i]) {
				move.addChange(new NoteChange(a, i));
			}
		}
	}

	@Override
	public int getDifficulty() {
		return 200;
	}

}
