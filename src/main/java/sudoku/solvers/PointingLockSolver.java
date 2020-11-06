package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class PointingLockSolver extends Solver {

	@Override
	public String getName() {
		return "Locked Candidates (Pointing)";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		for (int note = 1; note <= board.boxes.length; note++) {
			for (Cell[] box : board.boxes) {
				List<Cell> candidates = new ArrayList<>();
				for (Cell cell : box) {
					if (cell.notes[note]) {
						candidates.add(cell);
					}
				}
				if (!candidates.isEmpty()) {
					int row = candidates.get(0).row;
					int column = candidates.get(0).column;
					
					boolean sameRow = true;
					boolean sameColumn = true;
					for (int i = 1; i < candidates.size(); i++) {
						if (candidates.get(i).row != row) {
							sameRow = false;
						}
						if (candidates.get(i).column != column) {
							sameColumn = false;
						}
					}
					if (sameRow) {
						for (int i = 0; i < board.rows.length; i++) {
							if (board.rows[row][i].box != box[0].box) {
								move.addChange(new NoteChange(board.rows[row][i], note));
							}
						}
					} else if (sameColumn) {
						for (int i = 0; i < board.columns.length; i++) {
							if (board.columns[column][i].box != box[0].box) {
								move.addChange(new NoteChange(board.columns[column][i], note));
							}
						}
					} else {
						continue;
					}
					
					if (!move.isEmpty()) {
						String desc = this.getName() + " on " + note + " at ";
						
						for (Cell candidate : candidates) {
							desc += candidate.coordString();
						}
						
						move.description = desc;
						return;
					}
				}
			}
		}
	}

	@Override
	public int getDifficulty() {
		return 30;
	}

}
