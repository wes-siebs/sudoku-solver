package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public abstract class FishSolver extends Solver {

	protected Move getNextMove(Board board, int tupleSize) {
		Move nextMove = new Move();

		checkHouses(board.rows, board.columns, nextMove, tupleSize);

		if (nextMove.isEmpty()) {
			checkHouses(board.columns, board.rows, nextMove, tupleSize);
		}

		return nextMove;
	}

	private void checkHouses(Cell[][] houses, Cell[][] crossHouses, Move move, int tupleSize) {
		for (int i = 0; i < houses.length; i++) {
			List<Cell[]> candidates = new ArrayList<>();
			for (Cell[] house : houses) {
				int count = 0;
				for (Cell cell : house) {
					if (cell.notes[i]) {
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
				for (int l = 0; l < houses.length; l++) {
					boolean add = false;
					for (int pos : tuple) {
						if (candidates.get(pos)[l].notes[i]) {
							add = true;
							break;
						}
					}
					if (add) {
						intersectionLocs.add(l);
						for (int pos : tuple) {
							intersections.add(candidates.get(pos)[l]);
						}
					}
				}
				if (intersectionLocs.size() == tupleSize) {
					for (Integer intersection : intersectionLocs) {
						for (int l = 0; l < crossHouses.length; l++) {
							Cell changeCell = crossHouses[intersection][l];
							if (!intersections.contains(changeCell)) {
								move.addChange(new NoteChange(changeCell, i));
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
