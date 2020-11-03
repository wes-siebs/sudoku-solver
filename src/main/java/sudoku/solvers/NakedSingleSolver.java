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
	public Move getNextMove(Board board) {
		Move nextMove = new Move();

		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				int count = 0;
				int newValue = 0;
				for (int i = 0; i < cell.possibilities.length; i++) {
					if (cell.possibilities[i]) {
						if (count == 0) {
							count = 1;
							newValue = i;
						} else {
							count = 2;
							break;
						}
					}
				}
				if (count == 1) {
					this.addChanges(board, cell, newValue, nextMove);
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
