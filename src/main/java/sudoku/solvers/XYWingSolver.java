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
	protected void makeNextMove(Move move, Board board) {
		List<Cell> candidates = new ArrayList<Cell>();
		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				if (cell.getNumNotes() == 2) {
					candidates.add(cell);
				}
			}
		}
		
		Cell[] cells = new Cell[3];
		for (int i = 0; i < candidates.size(); i++) {
			cells[0] = candidates.get(i);
			if (cells[0].getValue() != 0) {
				continue;
			}
			for (int j = i + 1; j < candidates.size(); j++) {
				cells[1] = candidates.get(j);
				if (cells[1].getValue() != 0) {
					continue;
				}
				for (int k = j + 1; k < candidates.size(); k++) {
					cells[2] = candidates.get(k);
					if (cells[2].getValue() != 0) {
						continue;
					}
					int index = this.checkTriple(cells);
					if (index >= 0) {
						Cell key = cells[index];
						Cell p1 = cells[(index + 1) % 3];
						Cell p2 = cells[(index + 2) % 3];
						int toRemove = (p1.getNoteValue() | p2.getNoteValue()) ^ key.getNoteValue();
						toRemove = Utilities.unfold(toRemove);

						for (Cell[] row : board.rows) {
							for (Cell cell : row) {
								if (p1.canSee(cell) && p2.canSee(cell) && cell != key) {
									move.addChange(new NoteChange(cell, toRemove));
								}
							}
						}
						
						if (!move.isEmpty()) {
							String desc = this.getName() + " on ";
							for (int note = 1; note <= 9; note++) {
								if (key.getNote(note) || note == toRemove) {
									desc += note;
								}
							}
							desc += "\n\tKey: " + key.coordString();
							desc += "\n\tPincers: " + p1.coordString() + p2.coordString();	
							move.description = desc;
							return;
						}
					}
				}
			}
		}
	}
	
	private int checkTriple(Cell[] cells) {
		Cell c1 = cells[0];
		Cell c2 = cells[1];
		Cell c3 = cells[2];
		
		if ((c1.getNoteValue() ^ c2.getNoteValue()) == c3.getNoteValue()) {
			
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
		return 75;
	}

}
