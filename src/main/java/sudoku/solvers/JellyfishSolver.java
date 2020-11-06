package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class JellyfishSolver extends FishSolver {
	
	@Override
	public String getName() {
		return "Jellyfish";
	}

	@Override
	public Move getNextMove(Board board) {
		return super.getNextMove(board, 4);
	}

	@Override
	public int getDifficulty() {
		return 150;
	}

}
