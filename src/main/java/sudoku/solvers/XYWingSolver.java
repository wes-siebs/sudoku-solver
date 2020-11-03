package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class XYWingSolver extends Solver {

	@Override
	public String getName() {
		return "XY-Wing";
	}

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = new Move();
		
		List<Cell> candidates = new ArrayList<Cell>();
		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				int count = 0;
				for (boolean b : cell.possibilities) {
					count += b ? 1 : 0;
				}
				if (count == 2) {
					candidates.add(cell);
				}
			}
		}
		
		Cell[] cells = new Cell[3];
		for (int i = 0; i < candidates.size(); i++) {
			cells[0] = candidates.get(i);
			if (cells[0].value != 0) {
				continue;
			}
			for (int j = i + 1; j < candidates.size(); j++) {
				cells[1] = candidates.get(j);
				if (cells[1].value != 0) {
					continue;
				}
				for (int k = j + 1; k < candidates.size(); k++) {
					cells[2] = candidates.get(k);
					if (cells[2].value != 0) {
						continue;
					}
					int index = this.checkTriple(cells);
					if (index >= 0) {
						Cell key = cells[index];
						Cell p1 = cells[(index + 1) % 3];
						Cell p2 = cells[(index + 2) % 3];
						int toRemove = (p1.getIntNotes() | p2.getIntNotes()) ^ key.getIntNotes();
						toRemove = Utilities.unfold(toRemove);

						for (Cell[] row : board.rows) {
							for (Cell cell : row) {
								if (p1.canSee(cell) && p2.canSee(cell) && cell != key) {
									nextMove.addChange(new NoteChange(cell, toRemove));
								}
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
	
	private int checkTriple(Cell[] cells) {
		Cell c1 = cells[0];
		Cell c2 = cells[1];
		Cell c3 = cells[2];
		
		if ((c1.getIntNotes() ^ c2.getIntNotes()) == c3.getIntNotes()) {
			
			int count = 0;
			count += c1.canSee(c2) ? 1 : 0;
			count += c1.canSee(c3) ? 1 : 0;
			count += c2.canSee(c3) ? 1 : 0;
			
			if (count == 2) {
				if (c1.canSee(c2) && c1.canSee(c3)) {
					return 0;
				} else if (c2.canSee(c1) && c2.canSee(c3)) {
					return 1;
				} else {
					return 2;
				}
			}
		}
		
		return -1;
	}

	@Override
	public int getDifficulty() {
		return 7;
	}

}
