package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.FillChange;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public abstract class Solver {
	
	public abstract String getName();

	public Move getNextMove(Board board) {
		Move move = new Move();
		this.makeNextMove(move, board);
		move.difficulty = this.getDifficulty();
		
		return move;
	}
	
	protected abstract void makeNextMove(Move move, Board board);

	public abstract int getDifficulty();

	protected void addChanges(Board board, Cell cell, int fill, Move move) {
		move.addChange(new FillChange(cell, fill));
		for (int i = 1; i <= 9; i++) {
			move.addChange(new NoteChange(cell, i));
		}
		for (int i = 0; i < 9; i++) {
			move.addChange(new NoteChange(board.rows[cell.row][i], fill));
			move.addChange(new NoteChange(board.columns[cell.column][i], fill));
			move.addChange(new NoteChange(board.boxes[cell.box][i], fill));
		}
	}

}
