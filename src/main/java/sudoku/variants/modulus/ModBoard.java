package main.java.sudoku.variants.modulus;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;

public class ModBoard extends Board {
	
	public final List<ModCircle> modCircles;

	public ModBoard(Cell[][] rows, Cell[][] columns, Cell[][] boxes) {
		super(rows, columns, boxes);
		
		this.modCircles = new ArrayList<>();
	}

}
