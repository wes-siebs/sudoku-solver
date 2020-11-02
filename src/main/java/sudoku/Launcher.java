package main.java.sudoku;

import main.java.sudoku.components.Puzzle;

public class Launcher {

	public static void main(String[] args) {
		Frame frame = new Frame(new Puzzle("src/2star.txt"));
		frame.main_loop();
	}

}
