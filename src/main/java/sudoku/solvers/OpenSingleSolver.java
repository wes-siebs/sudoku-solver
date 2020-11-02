package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;

public class OpenSingleSolver extends Solver {

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = new Move();
		checkSections(board, board.rows, nextMove);

		if (nextMove.isEmpty()) {
			checkSections(board, board.columns, nextMove);

			if (nextMove.isEmpty()) {
				checkSections(board, board.boxes, nextMove);
			}
		}

		return nextMove;
	}

	private void checkSections(Board board, Cell[][] cellSections, Move move) {
		for (Cell[] section : cellSections) {
			int zeroCount = 0;
			for (Cell cell : section) {
				if (cell.value == 0) {
					zeroCount++;
				}
			}
			if (zeroCount == 1) {
				Cell moveCell = null;
				boolean[] valueTaken = new boolean[10];
				for (Cell cell : section) {
					if (cell.value == 0) {
						moveCell = cell;
					} else {
						valueTaken[cell.value] = true;
					}
				}
				for (int i = 1; i < valueTaken.length; i++) {
					if (!valueTaken[i]) {
						this.addChanges(board, moveCell, i, move);
						return;
					}
				}
			}
		}

		return;
	}

	@Override
	public int getDifficulty() {
		return 1;
	}

}
