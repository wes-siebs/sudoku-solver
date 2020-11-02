package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.FillChange;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class NakedSingleSolver extends Solver {

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
					nextMove.addChange(new FillChange(cell, newValue));
					for (int i = 1; i <= 9; i++) {
						nextMove.addChange(new NoteChange(cell, i));
					}
					for (int i = 0; i < 9; i++) {
						nextMove.addChange(new NoteChange(board.rows[cell.row][i], newValue));
						nextMove.addChange(new NoteChange(board.columns[cell.column][i], newValue));
						nextMove.addChange(new NoteChange(board.boxes[cell.box][i], newValue));
					}
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
