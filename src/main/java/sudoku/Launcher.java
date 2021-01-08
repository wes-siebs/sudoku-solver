package main.java.sudoku;

import main.java.sudoku.components.Drawer;
import main.java.sudoku.components.Puzzle;
import main.java.sudoku.variants.VariantDrawer;
import main.java.sudoku.variants.modulus.ModDrawer;

public class Launcher {

	public static void main(String[] args) {
		Puzzle puzzle = new Puzzle("resources/variants/test2.txt");
		VariantDrawer modDrawer = new ModDrawer();
		Drawer drawer = new Drawer();
		drawer.addVariant(modDrawer);
		Frame frame = new Frame(drawer);
		Listener listener = new Listener(puzzle, frame);
		listener.refresh();
	}

}
