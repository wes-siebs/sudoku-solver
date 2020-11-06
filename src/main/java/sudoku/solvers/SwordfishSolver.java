package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class SwordfishSolver extends FishSolver {
	
	@Override
	public String getName() {
		return "Swordfish";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		super.makeNextMove(move, board, 3);
	}

	@Override
	public int getDifficulty() {
		return 80;
	}

}
