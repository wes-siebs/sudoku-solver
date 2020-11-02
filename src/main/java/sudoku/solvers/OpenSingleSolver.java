package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;

public class OpenSingleSolver extends Solver {

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = checkSections(board.rows);
		
		if (nextMove == null) {
			nextMove = checkSections(board.columns);
			
			if (nextMove == null) {
				nextMove = checkSections(board.boxes);
			}
		}
		
		return nextMove;
	}
	
	private Move checkSections(Cell[][] cellSections) {
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
						return new Move(moveCell, moveCell.value, i);
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public int getDifficulty() {
		return 1;
	}
	
}
