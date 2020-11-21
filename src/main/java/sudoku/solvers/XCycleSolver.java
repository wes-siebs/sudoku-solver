package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class XCycleSolver extends Solver {

	@Override
	public String getName() {
		return "X-Cycle";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		for (int note = 1; note <= 9; note++) {
			List<Cell> candidates = new ArrayList<>();
			Queue<XCycle> cycles = new LinkedList<>();
			for (Cell[] row : board.rows) {
				for (Cell cell : row) {
					if (cell.getNote(note)) {
						candidates.add(cell);
						cycles.add(new XCycle(board, note, cell));
					}
				}
			}

			while (!cycles.isEmpty()) {
				XCycle cycle = cycles.poll();
				for (Cell cell : candidates) {
					XCycle newCycle = cycle.tryAddCell(cell);
					if (newCycle != null) {
						String desc = this.getName() + ": " + newCycle.toString();
						desc += "\n\tRemoves " + note + " from ";
						for (Cell pinched : candidates) {
							if (newCycle.pinches(pinched)) {
								desc += pinched.coordString();
								move.addChange(new NoteChange(pinched, note));
							}
						}
						if (!move.isEmpty()) {
							move.description = desc;
							return;
						}
						cycles.add(newCycle);
					}
				}
			}
		}
	}

	@Override
	public int getDifficulty() {
		return 125;
	}

}
