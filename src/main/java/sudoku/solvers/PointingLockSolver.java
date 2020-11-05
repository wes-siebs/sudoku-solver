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
	public Move getNextMove(Board board) {
		Move nextMove = new Move();
		
		for (int i = 0; i < board.boxes.length; i++) {
			for (Cell[] box : board.boxes) {
				List<Cell> candidates = new ArrayList<>();
				for (Cell cell : box) {
					if (cell.notes[i]) {
						candidates.add(cell);
					}
				}
				if (!candidates.isEmpty()) {
					int row = candidates.get(0).row;
					int column = candidates.get(0).column;
					
					boolean sameRow = true;
					boolean sameColumn = true;
					for (int j = 1; j < candidates.size(); j++) {
						if (candidates.get(j).row != row) {
							sameRow = false;
						}
						if (candidates.get(j).column != column) {
							sameColumn = false;
						}
					}
					if (sameRow) {
						for (int j = 0; j < board.rows.length; j++) {
							if (board.rows[row][j].box != box[0].box) {
								nextMove.addChange(new NoteChange(board.rows[row][j], i));
							}
						}
						if (!nextMove.isEmpty()) {
							return nextMove;
						}
					}
					if (sameColumn) {
						for (int j = 0; j < board.columns.length; j++) {
							if (board.columns[column][j].box != box[0].box) {
								nextMove.addChange(new NoteChange(board.columns[column][j], i));
							}
						}
						if (!nextMove.isEmpty()) {
							return nextMove;
						}
					}
				}
			}
		}

		return nextMove;
	}

	@Override
	public int getDifficulty() {
		return 3;
	}

}
