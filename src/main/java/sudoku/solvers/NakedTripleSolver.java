package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class NakedTripleSolver extends Solver {

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
	
	private void checkHouse(Cell[] house, Move move) {
		for (int i = 0; i < house.length; i++) {
			if (house[i].value != 0) {
				continue;
			}
			for (int j = i + 1; j < house.length; j++) {
				if (house[j].value != 0) {
					continue;
				}
				for (int k = j + 1; k < house.length; k++) {
					if (house[k].value != 0) {
						continue;
					}
					
					List<Integer> notes = overlap(house[i], house[j], house[k]);
					if (notes.size() == 3) {
						for (Cell cell : house) {
							if (cell != house[i] && cell != house[j] && cell != house[k]) {
								for (Integer note : notes)
								if (cell.possibilities[note]) {
									move.addChange(new NoteChange(cell, note));
								}
							}
						}
						
						if (!move.isEmpty()) {
							return;
						}
					}
				}
			}
		}
	}
	
	private List<Integer> overlap(Cell c1, Cell c2, Cell c3) {
		List<Integer> notes = new ArrayList<>();
		for (int i = 1; i < c1.possibilities.length; i++) {
			if (c1.possibilities[i] || c2.possibilities[i] || c3.possibilities[i]) {
				notes.add(i);
			}
		}
		return notes;
	}

	@Override
	public int getDifficulty() {
		return 3;
	}

}
