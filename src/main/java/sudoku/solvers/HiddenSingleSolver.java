package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public class HiddenSingleSolver extends Solver {

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = new Move();
		
		return nextMove;
	}

	@Override
	public int getDifficulty() {
		return 2;
	}

}
