package main.java.sudoku;

import main.java.sudoku.components.Puzzle;
import main.java.sudoku.solvers.SolverList;

public class Launcher {

	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle("resources/test/SimpleColoring4", SolverList.solverList);
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
