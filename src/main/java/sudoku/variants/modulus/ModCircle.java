package main.java.sudoku.variants.modulus;

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
	
	public String toString() {
		if (this.c1.row == this.c2.row) {
			return "r" + this.c1.row + "c" + this.c1.column + this.c2.column;
		} else {
			return "r" + this.c1.row + this.c2.row + "c" + this.c1.column;
		}
	}

}
