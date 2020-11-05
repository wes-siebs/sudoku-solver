package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;

public class OpenSingleSolver extends Solver {
	
	@Override
	public String getName() {
		return "Open Single";
	}

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = new Move();

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
					this.addChanges(board, cell, note, nextMove);
					return nextMove;
				}
			}
		}

		return nextMove;
	}

	@Override
	public int getDifficulty() {
		return 1;
	}

}
