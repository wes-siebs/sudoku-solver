package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class NakedQuadSolver extends NakedTupleSolver {
	
	@Override
	public String getName() {
		return "Naked Quad";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		super.makeNextMove(move, board, 4);
	}

	@Override
	public int getDifficulty() {
		return 20;
	}

}
