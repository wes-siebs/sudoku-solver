package main.java.sudoku.components;

public class Cell {

	public final int row;
	public final int column;
	public final int box;
	private boolean[] notes;
	private int noteValue;
	private int value;

	public Cell(int value, int row, int column, int box) {
		this(value, row, column, box, true);
	}

	public Cell(int value, int row, int column, int box, boolean truth) {
		this.row = row;
		this.column = column;
		this.box = box;
		this.value = value;
		this.notes = new boolean[10];

		this.noteValue = 0;
		if (value == 0 && truth) {
			this.noteValue = 1022;
			for (int i = 1; i < 10; i++) {
				this.notes[i] = truth;
			}
		}
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int value) {
		this.value = value;
		this.noteValue = 0;
	}

	public boolean getNote(int note) {
		return this.notes[note];
	}

	public void setNote(int note, boolean value) {
		if (this.notes[note] != value) {
			this.notes[note] = value;
			if (value) {
				this.noteValue += 1 << note;
			} else {
				this.noteValue -= 1 << note;
			}
		}
	}

	public int getNoteValue() {
		return this.noteValue;
	}

	public int getNumNotes() {
		int count = 0;
		for (boolean note : this.notes) {
			count += note ? 1 : 0;
		}
		return count;
	}

	public boolean canSee(Cell cell) {
		if (this == cell) {
			return false;
		} else if (this.row == cell.row) {
			return true;
		} else if (this.column == cell.column) {
			return true;
		} else if (this.box == cell.box) {
			return true;
		} else {
			return false;
		}
	}

	public String toString() {
		return this.value + "";
	}

	public String coordString() {
		return "r" + (this.row + 1) + "c" + (this.column + 1);
	}

}
