package main.java.sudoku.variants;

import main.java.sudoku.components.Cell;

public class ModCircle {
	
	public final Cell c1;
	public final Cell c2;
	public final int mod;
	
	public ModCircle(Cell c1, Cell c2, int mod) {
		this.c1 = c1;
		this.c2 = c2;
		this.mod = mod;
	}

}
