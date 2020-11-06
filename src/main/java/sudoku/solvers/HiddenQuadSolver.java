package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class HiddenQuadSolver extends HiddenTupleSolver {
	
	@Override
	public String getName() {
		return "Hidden Quad";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		super.makeNextMove(move, board, 4);
	}

	@Override
	public int getDifficulty() {
		return 25;
	}

}
