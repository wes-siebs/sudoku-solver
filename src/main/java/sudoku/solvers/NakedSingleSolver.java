package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;

public class NakedSingleSolver extends Solver {
	
	@Override
	public String getName() {
		return "Naked Single";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				if (cell.getNumNotes() == 1) {
					int note = 0;
					for (int i = 0; i < cell.notes.length; i++) {
						if (cell.notes[i]) {
							note = i;
							break;
						}
					}
					this.addChanges(board, cell, note, move);
					return;
				}
			}
		}
	}

	@Override
	public int getDifficulty() {
		return 0;
	}

}
