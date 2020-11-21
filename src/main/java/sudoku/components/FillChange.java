package main.java.sudoku.components;

public class FillChange extends Change {
	
	public Cell cell;
	public int oldValue;
	public int newValue;
	
	public FillChange(Cell cell, int newValue) {
		this.cell = cell;
		this.oldValue = cell.getValue();
		this.newValue = newValue;
	}

	@Override
	public void apply() {
		this.cell.setValue(this.newValue);
	}

	@Override
	public void unapply() {
		this.cell.setValue(this.oldValue);
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
