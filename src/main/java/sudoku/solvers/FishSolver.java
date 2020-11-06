package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public abstract class FishSolver extends Solver {

	protected void makeNextMove(Move move, Board board, int tupleSize) {
		checkHouses(board.rows, board.columns, move, tupleSize);

		if (move.isEmpty()) {
			checkHouses(board.columns, board.rows, move, tupleSize);
		}
	}

	private void checkHouses(Cell[][] houses, Cell[][] crossHouses, Move move, int tupleSize) {
		for (int note = 1; note <= houses.length; note++) {
			List<Cell[]> candidates = new ArrayList<>();
			for (Cell[] house : houses) {
				int count = 0;
				for (Cell cell : house) {
					if (cell.notes[note]) {
						count++;
					}
				}
				if (count > 0 && count <= tupleSize) {
					candidates.add(house);
				}
			}

			if (candidates.size() < tupleSize) {
				continue;
			}

			int[][] tuples = Utilities.nCkTuples(candidates.size(), tupleSize, 0);
			for (int tuple[] : tuples) {
				List<Cell> intersections = new ArrayList<>();
				List<Integer> intersectionLocs = new ArrayList<>();
				for (int i = 0; i < houses.length; i++) {
					boolean add = false;
					for (int pos : tuple) {
						if (candidates.get(pos)[i].notes[note]) {
							add = true;
							break;
						}
					}
					if (add) {
						intersectionLocs.add(i);
						for (int pos : tuple) {
							intersections.add(candidates.get(pos)[i]);
						}
					}
				}
				if (intersectionLocs.size() == tupleSize) {
					for (Integer intersection : intersectionLocs) {
						for (int i = 0; i < crossHouses.length; i++) {
							Cell changeCell = crossHouses[intersection][i];
							if (!intersections.contains(changeCell)) {
								move.addChange(new NoteChange(changeCell, note));
							}
						}
					}
					if (!move.isEmpty()) {
						String desc = this.getName() + " on " + note + " at ";
						for (Cell cell : intersections) {
							desc += cell.coordString();
						}
						move.description = desc;
						return;
					}
				}
			}
		}
	}

}
