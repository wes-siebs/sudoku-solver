package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class NakedPairSolver extends NakedTupleSolver {
	
	@Override
	public String getName() {
		return "Naked Pair";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		super.makeNextMove(move, board, 2);
	}

	@Override
	public int getDifficulty() {
		return 10;
	}

}
