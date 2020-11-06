package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;

public class HiddenSingleSolver extends Solver {
	
	@Override
	public String getName() {
		return "Hidden Single";
	}

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = new Move();

		for (int i = 1; i <= 9; i++) {
			for (int j = 0; j < 9; j++) {
				int rowCount = 0;
				Cell rowCell = null;
				int columnCount = 0;
				Cell columnCell = null;
				int boxCount = 0;
				Cell boxCell = null;

				for (int k = 0; k < 9; k++) {
					if (board.rows[j][k].notes[i]) {
						rowCount++;
						rowCell = board.rows[j][k];
					}
					if (board.columns[j][k].notes[i]) {
						columnCount++;
						columnCell = board.columns[j][k];
					}
					if (board.boxes[j][k].notes[i]) {
						boxCount++;
						boxCell = board.boxes[j][k];
					}
				}

				if (rowCount == 1) {
					this.addChanges(board, rowCell, i, nextMove);
					return nextMove;
				}

				if (columnCount == 1) {
					this.addChanges(board, columnCell, i, nextMove);
					return nextMove;
				}

				if (boxCount == 1) {
					this.addChanges(board, boxCell, i, nextMove);
					return nextMove;
				}
			}
		}

		return nextMove;
	}

	@Override
	public int getDifficulty() {
		return 5;
	}

}
