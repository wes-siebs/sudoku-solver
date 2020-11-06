package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class NakedTripleSolver extends NakedTupleSolver {
	
	@Override
	public String getName() {
		return "Naked Triple";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		super.makeNextMove(move, board, 3);
	}

	@Override
	public int getDifficulty() {
		return 15;
	}

}
