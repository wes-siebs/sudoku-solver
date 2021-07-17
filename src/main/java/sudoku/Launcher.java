package main.java.sudoku;

import main.java.sudoku.components.Drawer;
import main.java.sudoku.components.Puzzle;
import main.java.sudoku.variants.VariantDrawer;
import main.java.sudoku.variants.modulus.ModDrawer;

public class Launcher {

	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle("resources/puzzles/formom");
		//VariantDrawer modDrawer = new ModDrawer();
		Drawer drawer = new Drawer();
		//drawer.addVariant(modDrawer);
		Frame frame = new Frame(drawer);
		Listener listener = new Listener(puzzle, frame);
		listener.refresh();
		while(true) {
			listener.refresh();
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				
			}
		}
	}

}
