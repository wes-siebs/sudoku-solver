package main.java.sudoku.components;

public class Board {

	public final Cell[][] rows;
	public final Cell[][] columns;
	public final Cell[][] boxes;

	public Board(Cell[][] rows, Cell[][] columns, Cell[][] boxes) {
		this.rows = rows;
		this.columns = columns;
		this.boxes = boxes;
	}

	public void clear() {
		for (Cell[] row : rows) {
			for (Cell cell : row) {
				cell.setValue(0);
				for (int i = 0; i < 10; i++) {
					cell.setNote(i, true);
				}
			}
		}
	}

	public void set(int r, int c, int value) {
		Cell c1 = this.rows[r][c];
		c1.setValue(value);
		for (int i = 0; i < 10; i++) {
			c1.setNote(i, false);
		}

		for (Cell[] row : rows) {
			for (Cell c2 : row) {
				if (c1.canSee(c2)) {
					c2.setNote(value, false);
				}
			}
		}
	}

}
