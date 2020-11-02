package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Move;

public abstract class Solver {
	
	public abstract Move getNextMove(Board board);
	
	public abstract int getDifficulty();

}
