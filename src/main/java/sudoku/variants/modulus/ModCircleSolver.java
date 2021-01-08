package main.java.sudoku.variants.modulus;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Change;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;
import main.java.sudoku.solvers.Solver;

public class ModCircleSolver extends Solver {

	@Override
	public String getName() {
		return "Modulus Circle";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		if (!(board instanceof ModBoard)) {
			return;
		}
		
		ModBoard modBoard = (ModBoard) board;
		
		for (ModCircle circle : modBoard.modCircles) {
			List<Change> changes = getRemovals(circle.c1, circle.c2, circle.mod);
			changes.addAll(getRemovals(circle.c2, circle.c1, circle.mod));
			
			for (Change change : changes) {
				move.addChange(change);
			}
			
			if (!move.isEmpty()) {
				String desc = "Modulus Circle (" + circle.mod + ") on " + circle.c1.coordString() + circle.c2.coordString();
				move.description = desc;
				return;
			}
		}
	}
	
	public List<Change> getRemovals(Cell c1, Cell c2, int mod) {
		List<Change> changes = new ArrayList<>();
		
		if (c1.getValue() == 0) {
			boolean[] possible = new boolean[10];
			if (c2.getValue() == 0) {
				for (int i = 1; i <= 9; i++) {
					if (c2.getNote(i)) {
						for (int j = 1; j <= 9; j++) {
							if (i != j && i % mod == j % mod) {
								possible[j] = true;
							}
						}
					}
				}
			} else {
				for (int j = 1; j <= 9; j++) {
					if (c2.getValue() != j && c2.getValue() % mod == j % mod) {
						possible[j] = true;
					}
				}
			}
			for (int i = 1; i <= 9; i++) {
				if (c1.getNote(i) && !possible[i]) {
					changes.add(new NoteChange(c1, i));
				}
			}
		}
		
		return changes;
	}

	@Override
	public int getDifficulty() {
		return -1;
	}

}
