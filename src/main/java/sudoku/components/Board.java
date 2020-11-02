package main.java.sudoku.components;

public class Board {
	
	private final int unit;
	public final Cell[][] rows;
	public final Cell[][] columns;
	public final Cell[][] boxes;
	
	public Board(int[][] values) {
		this.unit = (int) Math.sqrt(values.length);
		
		this.rows = new Cell[values.length][values[0].length];
		this.columns = new Cell[values[0].length][values.length];
		this.boxes = new Cell[values.length][values[0].length];

		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values.length; j++) {
				Cell cell = new Cell(values[i][j], i, j, this.getBox(i, j));
				this.rows[i][j] = cell;
				this.columns[j][i] = cell;
				this.boxes[cell.box][this.getBoxPos(i, j)] = cell;
			}
		}
		
		for (Cell[] row : this.rows) {
			for (Cell checkedCell : row) {
				for (Cell changedCell : row) {
					changedCell.possibilities[checkedCell.value] = false;
				}
			}
		}
		
		for (Cell[] column : this.columns) {
			for (Cell checkedCell : column) {
				for (Cell changedCell : column) {
					changedCell.possibilities[checkedCell.value] = false;
				}
			}
		}
		
		for (Cell[] box : this.boxes) {
			for (Cell checkedCell : box) {
				for (Cell changedCell : box) {
					changedCell.possibilities[checkedCell.value] = false;
				}
			}
		}
	}
	
	public int getBox(int row, int column) {
		return (row / this.unit) * this.unit + (column / this.unit);
	}
	
	public int getBoxPos(int row, int column) {
		return (row % this.unit) * this.unit + (column % this.unit);
	}

}
