package main.java.sudoku;

import main.java.sudoku.components.Puzzle;
import main.java.sudoku.solvers.SolverList;

public class Launcher {

	public static void main(String[] args) {
		Frame frame = new Frame(new Puzzle("resources/5star2.txt", SolverList.solverList));

		while (true) {
			frame.draw();

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
