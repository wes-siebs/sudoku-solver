package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.FillChange;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

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
						move.addChange(new FillChange(moveCell, i));
						for (int j = 1; j <= 9; j++) {
							move.addChange(new NoteChange(moveCell, j));
						}
						for (int j = 0; j < 9; j++) {
							move.addChange(new NoteChange(board.rows[moveCell.row][j], i));
							move.addChange(new NoteChange(board.columns[moveCell.column][j], i));
							move.addChange(new NoteChange(board.boxes[moveCell.box][j], i));
						}
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
