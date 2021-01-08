package main.java.sudoku;

import main.java.sudoku.components.Puzzle;
import main.java.sudoku.solvers.Solver;
import main.java.sudoku.solvers.SolverList;

public class Launcher {

	public static void main(String[] args) {
		Solver[] solvers = SolverList.getInstance().solvers;
		Puzzle puzzle = new Puzzle("resources/variants/test2.txt", solvers);
		Frame frame = new Frame();
		Listener listener = new Listener(puzzle, frame);
		listener.refresh();
	}

}
