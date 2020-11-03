package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class NakedTripleSolver extends NakedTupleSolver {
	
	@Override
	public String getName() {
		return "Naked Triple";
	}

	@Override
	public Move getNextMove(Board board) {
		return super.getNextMove(board, 3);
	}

	@Override
	public int getDifficulty() {
		return 4;
	}

}
