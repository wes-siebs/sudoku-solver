package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class HiddenTripleSolver extends HiddenTupleSolver {

	@Override
	public Move getNextMove(Board board) {
		return super.getNextMove(board, 3);
	}

	@Override
	public int getDifficulty() {
		return 4;
	}

}
