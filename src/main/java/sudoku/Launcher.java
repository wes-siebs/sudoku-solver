package main.java.sudoku;

import main.java.sudoku.components.Puzzle;
import main.java.sudoku.solvers.Solver;
import main.java.sudoku.solvers.SolverList;

public class Launcher {

	public static void main(String[] args) {
		Solver[] solvers = SolverList.getInstance().solvers;
		Puzzle puzzle = new Puzzle("resources/8star.txt", solvers);
		Frame frame = new Frame(puzzle);

		while (true) {
			frame.draw(puzzle.getBoard());

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
