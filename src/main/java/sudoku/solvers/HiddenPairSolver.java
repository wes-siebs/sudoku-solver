package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class HiddenPairSolver extends HiddenTupleSolver {
	
	@Override
	public String getName() {
		return "Hidden Pair";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		super.makeNextMove(move, board, 2);
	}

	@Override
	public int getDifficulty() {
		return 15;
	}

}
