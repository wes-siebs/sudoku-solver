package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class NakedPairSolver extends NakedTupleSolver {
	
	@Override
	public String getName() {
		return "Naked Pair";
	}

	@Override
	public Move getNextMove(Board board) {
		return super.getNextMove(board, 2);
	}

	@Override
	public int getDifficulty() {
		return 20;
	}

}
