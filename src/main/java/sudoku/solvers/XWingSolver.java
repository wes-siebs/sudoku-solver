package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class XWingSolver extends Solver {

	@Override
	public Move getNextMove(Board board) {
		Move nextMove = new Move();

		checkHouses(board.rows, board.columns, nextMove);

		if (nextMove.isEmpty()) {
			System.out.println("f");
			checkHouses(board.columns, board.rows, nextMove);
		}

		return nextMove;
	}

	public void checkHouses(Cell[][] houses, Cell[][] crossHouses, Move move) {
		for (int i = 0; i < houses.length; i++) {
			List<Cell[]> candidates = new ArrayList<>();
			for (Cell[] house : houses) {
				int count = 0;
				for (Cell cell : house) {
					if (cell.possibilities[i]) {
						count++;
					}
				}
				if (count > 0 && count <= 2) {
					candidates.add(house);
				}
			}

			for (int j = 0; j < candidates.size(); j++) {
				Cell[] row1 = candidates.get(j);
				for (int k = j + 1; k < candidates.size(); k++) {
					Cell[] row2 = candidates.get(k);
					List<Cell> intersections = new ArrayList<>();
					List<Integer> intersectionLocs = new ArrayList<>();
					for (int l = 0; l < houses.length; l++) {
						boolean add = false;
						if (row1[l].possibilities[i]) {
							add = true;
						}
						if (row2[l].possibilities[i]) {
							add = true;
						}
						if (add) {
							intersectionLocs.add(l);
							intersections.add(row1[l]);
							intersections.add(row2[l]);
						}
					}
					if (intersections.size() == 2 * 2) {
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

	@Override
	public int getDifficulty() {
		return 4;
	}

}
