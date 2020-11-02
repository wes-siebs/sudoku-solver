package main.java.sudoku.solvers;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class NakedPairSolver extends Solver {

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = new Move();

		for (int i = 0; i < 9; i++) {
			checkHouse(board.rows[i], nextMove);
			if (nextMove.isEmpty()) {
				checkHouse(board.columns[i], nextMove);
				if (nextMove.isEmpty()) {
					checkHouse(board.boxes[i], nextMove);
					if (!nextMove.isEmpty()) {
						break;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}

		return nextMove;
	}

	public void checkHouse(Cell[] house, Move move) {
		for (int i = 0; i < house.length; i++) {
			if (house[i].value != 0) {
				continue;
			}
			for (int j = i + 1; j < house.length; j++) {
				if (house[j].value != 0) {
					continue;
				}
				int noteCount = 0;
				int[] notes = new int[2];
				int k = 1;
				for (; k <= house.length; k++) {
					if (house[i].possibilities[k] == house[j].possibilities[k]) {
						if (house[i].possibilities[k]) {
							if (noteCount < 2) {
								notes[noteCount] = k;
								noteCount++;
							} else {
								break;
							}
						}
					} else {
						break;
					}
				}
				if (k == house.length + 1 && noteCount == 2) {
					for (Cell cell : house) {
						if (cell != house[i] && cell != house[j]) {
							if (cell.possibilities[notes[0]]) {
								move.addChange(new NoteChange(cell, notes[0]));
							}
							if (cell.possibilities[notes[1]]) {
								move.addChange(new NoteChange(cell, notes[1]));
							}
						}
					}

					return;
				}
			}
		}
	}

	@Override
	public int getDifficulty() {
		return 2;
	}

}
