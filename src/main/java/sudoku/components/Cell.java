package main.java.sudoku.components;

public class Cell {
	
	public int row;
	public int column;
	public int box;
	public int value;
	public boolean[] possibilities;
	
	public Cell(int value, int row, int column, int box) {
		this.row = row;
		this.column = column;
		this.box = box;
		this.value = value;
		this.possibilities = new boolean[10];

		if (value == 0) {
			for (int i = 1; i < 10; i++) {
				this.possibilities[i] = true;
			}
		}
	}
	
	public String toString() {
		return this.value + "";
	}

}
