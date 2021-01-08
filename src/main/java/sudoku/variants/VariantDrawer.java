package main.java.sudoku.variants;

import main.java.sudoku.components.Board;

public abstract class VariantDrawer {
	
	public abstract void drawVariant(Board board);
	
	public abstract boolean overFrame();

}
