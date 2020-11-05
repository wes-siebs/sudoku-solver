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

}
