package main.java.sudoku.components;

public class FillChange extends Change {
	
	private Cell cell;
	private int newValue;
	
	public FillChange(Cell cell, int newValue) {
		this.cell = cell;
		this.newValue = newValue;
	}

	@Override
	public void apply() {
		this.cell.value = newValue;
	}

	@Override
	public void unapply() {
		this.cell.value = 0;
	}

}
