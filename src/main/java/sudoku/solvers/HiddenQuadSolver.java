package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class HiddenQuadSolver extends HiddenTupleSolver {

	@Override
	public Move getNextMove(Board board) {
		return super.getNextMove(board, 4);
	}

	@Override
	public int getDifficulty() {
		return 4;
	}

}