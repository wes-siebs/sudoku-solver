package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class ClaimingLockSolver extends Solver {

	@Override
	public String getName() {
		return "Locked Candidates (Claiming)";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		if (checkHouses(board, board.rows, move)) {
			checkHouses(board, board.columns, move);
		}
	}

	private boolean checkHouses(Board board, Cell[][] houses, Move move) {
		for (int i = 1; i <= houses.length; i++) {
			for (Cell[] house : houses) {
				List<Cell> candidates = new ArrayList<>();
				for (Cell cell : house) {
					if (cell.getNote(i)) {
						candidates.add(cell);
					}
				}
				if (!candidates.isEmpty()) {
					int box = candidates.get(0).box;
					
					boolean sameBox = true;
					String desc = this.getName() + " on " + i + " at ";
					for (Cell candidate : candidates) {
						desc += candidate.coordString();
						if (candidate.box != box) {
							sameBox = false;
							break;
						}
					}
					if (sameBox) {
						for (Cell cell : board.boxes[box]) {
							if (!candidates.contains(cell)) {
								move.addChange(new NoteChange(cell, i));
							}
						}
						if (!move.isEmpty()) {
							move.description = desc;
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	@Override
	public int getDifficulty() {
		return 30;
	}

}
