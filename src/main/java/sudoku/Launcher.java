package main.java.sudoku;

import main.java.sudoku.components.Puzzle;

public class Launcher {

	public static void main(String[] args) {
		Frame frame = new Frame(new Puzzle("src/test2.txt"));
		frame.main_loop();
	}

}
