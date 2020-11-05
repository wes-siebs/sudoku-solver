package main.java.sudoku.components;

public class FillChange extends Change {
	
	public Cell cell;
	public int oldValue;
	public int newValue;
	
	public FillChange(Cell cell, int newValue) {
		this.cell = cell;
		this.oldValue = cell.value;
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

	@Override
	public boolean isValid() {
		return this.oldValue == 0;
	}
	
	@Override
	public String toString() {
		return "Fill " + cell.coordString() + " with " + newValue;
	}

}
