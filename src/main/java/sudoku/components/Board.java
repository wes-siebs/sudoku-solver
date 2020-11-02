package main.java.sudoku.components;

public class Board {
	
	private int unit;
	public Cell[][] rows;
	public Cell[][] columns;
	public Cell[][] boxes;
	
	public Board(int[][] values) {
		this.unit = (int) Math.sqrt(values.length);
		
		this.rows = new Cell[values.length][values[0].length];
		this.columns = new Cell[values[0].length][values.length];
		this.boxes = new Cell[values.length][values[0].length];

		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				Cell cell = new Cell(values[i][j], i, j);
				this.rows[i][j] = cell;
				this.columns[j][i] = cell;
				this.boxes[this.getBox(i, j)][this.getBoxPos(i, j)] = cell;
			}
		}
	}
	
	public int getBox(int row, int column) {
		return (row / this.unit) * this.unit + (column / this.unit);
	}
	
	public int getBoxPos(int row, int column) {
		return (row % this.unit) * this.unit + (column % this.unit);
	}
	
	public void applyMove(Move move) {
		this.rows[move.cell.row][move.cell.column].value = move.newValue;
	}
	
	public void unapplyMove(Move move) {
		this.rows[move.cell.row][move.cell.column].value = move.oldValue;
	}

}
