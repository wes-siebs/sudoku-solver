package main.java.sudoku;

import main.java.sudoku.components.Puzzle;

public class Launcher {

	public static void main(String[] args) {
		Frame frame = new Frame(new Puzzle("src/4star.txt"));

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
