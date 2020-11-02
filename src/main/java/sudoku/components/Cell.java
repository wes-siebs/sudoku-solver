package main.java.sudoku.components;

public class Cell {
	
	public int row;
	public int column;
	public int value;
	
	public Cell(int value, int row, int column) {
		this.row = row;
		this.column = column;
		this.value = value;
	}
	
	public String toString() {
		return this.value + "";
	}

}
