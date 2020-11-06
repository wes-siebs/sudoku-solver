package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class XWingSolver extends FishSolver {
	
	@Override
	public String getName() {
		return "X-Wing";
	}

	@Override
	public Move getNextMove(Board board) {
		return super.getNextMove(board, 2);
	}

	@Override
	public int getDifficulty() {
		return 40;
	}

}
