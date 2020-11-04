package main.java.sudoku.components;

public class NoteChange extends Change {
	
	public Cell cell;
	public int note;
	public boolean oldValue;
	
	public NoteChange(Cell cell, int note) {
		this.cell = cell;
		this.note = note;
		this.oldValue = cell.possibilities[note];
	}

	@Override
	public void apply() {
		this.cell.possibilities[note] = false;
	}

	@Override
	public void unapply() {
		this.cell.possibilities[note] = oldValue;
	}

	@Override
	public boolean isValid() {
		return this.oldValue;
	}

}
