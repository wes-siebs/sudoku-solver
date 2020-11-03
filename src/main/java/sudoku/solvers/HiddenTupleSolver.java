package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public abstract class HiddenTupleSolver extends Solver {

	protected Move getNextMove(Board board, int tupleSize) {
		Move nextMove = new Move();

		int[][] tuples = Utilities.nCkTuples(board.rows.length, tupleSize, 1);

		for (int[] tuple : tuples) {
			for (int i = 0; i < board.rows.length; i++) {
				this.checkHouse(board.rows[i], tuple, nextMove);
				if (!nextMove.isEmpty()) {
					return nextMove;
				}
				this.checkHouse(board.columns[i], tuple, nextMove);
				if (!nextMove.isEmpty()) {
					return nextMove;
				}
				this.checkHouse(board.boxes[i], tuple, nextMove);
				if (!nextMove.isEmpty()) {
					return nextMove;
				}
			}
		}

		return nextMove;
	}

	private void checkHouse(Cell[] house, int[] tuple, Move move) {
		boolean[] used = new boolean[tuple.length];
		List<Cell> cells = new ArrayList<>();
		for (Cell cell : house) {
			boolean add = false;
			for (int i = 0; i < tuple.length; i++) {
				if (cell.possibilities[tuple[i]]) {
					add = true;
					used[i] = true;
				}
			}
			if (add) {
				cells.add(cell);
			}
		}

		boolean valid = true;
		for (boolean check : used) {
			valid &= check;
		}

		if (valid && cells.size() == tuple.length) {
			for (int value : tuple) {
				for (Cell cell : house) {
					if (!cells.contains(cell)) {
						move.addChange(new NoteChange(cell, value));
					}
				}
			}

			for (int i = 1; i < house.length; i++) {
				boolean change = true;
				for (int value : tuple) {
					if (i == value) {
						change = false;
						break;
					}
				}
				if (change) {
					for (Cell cell : cells) {
						move.addChange(new NoteChange(cell, i));
					}
				}
			}
		}
	}

}
