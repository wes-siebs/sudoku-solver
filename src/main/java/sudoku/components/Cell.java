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
	
	public String coordString() {
		return "(" + this.row + "," + this.column + ")";
	}
	
	public int getNumNotes() {
		int count = 0;
		for (boolean note : this.possibilities) {
			count += note ? 1 : 0;
		}
		return count;
	}

	public int getIntNotes() {
		int value = 0;
		for (int i = 0; i < this.possibilities.length; i++) {
			if (this.possibilities[i]) {
				value |= 1 << i;
			}
		}
		return value;
	}
	
	public boolean canSee(Cell cell) {
		if (this.row == cell.row) {
			return true;
		} else if (this.column == cell.column) {
			return true;
		} else if (this.box == cell.box) {
			return true;
		} else {
			return false;
		}
	}

}
