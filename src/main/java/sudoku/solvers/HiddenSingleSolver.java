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
	protected void makeNextMove(Move move, Board board) {
		for (int i = 1; i <= 9; i++) {
			for (int j = 0; j < 9; j++) {
				int rowCount = 0;
				Cell rowCell = null;
				int columnCount = 0;
				Cell columnCell = null;
				int boxCount = 0;
				Cell boxCell = null;

				for (int k = 0; k < 9; k++) {
					if (board.rows[j][k].getNote(i)) {
						rowCount++;
						rowCell = board.rows[j][k];
					}
					if (board.columns[j][k].getNote(i)) {
						columnCount++;
						columnCell = board.columns[j][k];
					}
					if (board.boxes[j][k].getNote(i)) {
						boxCount++;
						boxCell = board.boxes[j][k];
					}
				}

				if (rowCount == 1) {
					this.addChanges(board, rowCell, i, move);
					move.description = "Hidden Single at " + rowCell.coordString();
					return;
				}

				if (columnCount == 1) {
					this.addChanges(board, columnCell, i, move);
					move.description = "Hidden Single at " + columnCell.coordString();
					return;
				}

				if (boxCount == 1) {
					this.addChanges(board, boxCell, i, move);
					move.description = "Hidden Single at " + boxCell.coordString();
					return;
				}
			}
		}
	}

	@Override
	public int getDifficulty() {
		return 5;
	}

}
