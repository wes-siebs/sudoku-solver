package main.java.sudoku.components;

public class Move {
	
	public Cell cell;
	public int oldValue;
	public int newValue;
	
	public Move(Cell cell, int oldValue, int newValue) {
		this.cell = cell;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

}
