package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public abstract class NakedTupleSolver extends Solver {

	protected Move getNextMove(Board board, int tupleSize) {
		Move nextMove = new Move();

		int[][] tuples = Utilities.nCkTuples(board.rows.length, tupleSize, 0);
		for (int[] tuple : tuples) {
			for (int i = 0; i < 9; i++) {
				checkHouse(board.rows[i], nextMove, tuple);
				if (!nextMove.isEmpty()) {
					return nextMove;
				}
				checkHouse(board.columns[i], nextMove, tuple);
				if (!nextMove.isEmpty()) {
					return nextMove;
				}
				checkHouse(board.boxes[i], nextMove, tuple);
				if (!nextMove.isEmpty()) {
					return nextMove;
				}
			}
		}

		return nextMove;
	}

	private void checkHouse(Cell[] house, Move move, int[] tuple) {
		boolean skip = false;
		for (int i : tuple) {
			if (house[i].value != 0) {
				skip = true;
				break;
			}
		}
		if (skip) {
			return;
		}

		List<Integer> notes = this.overlap(tuple, house);
		if (notes.size() == tuple.length) {
			for (int i = 0; i < house.length; i++) {
				skip = false;
				for (int j : tuple) {
					if (i == j) {
						skip = true;
						break;
					}
				}
				if (!skip) {
					for (Integer note : notes) {
						move.addChange(new NoteChange(house[i], note));
					}
				}
			}
			
			if (!move.isEmpty()) {
				return;
			}
		}
	}

	private List<Integer> overlap(int[] indices, Cell[] cells) {
		List<Integer> notes = new ArrayList<>();
		for (int i = 1; i <= cells.length; i++) {
			for (int index : indices) {
				if (cells[index].notes[i]) {
					notes.add(i);
					break;
				}
			}
		}
		return notes;
	}

}
