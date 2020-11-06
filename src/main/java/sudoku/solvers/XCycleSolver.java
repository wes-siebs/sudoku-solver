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
					if (cell.notes[note]) {
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
						if (newCycle.loops()) {
							if (newCycle.chain.size() % 2 == 0) {
								this.handleDisLoop(newCycle, move);
							} else {
								this.handleConLoop(newCycle, candidates, move);
							}

							if (!move.isEmpty()) {
								return;
							}
						} else {
							for (Cell pinched : candidates) {
								if (newCycle.pinches(pinched)) {
									move.addChange(new NoteChange(pinched, note));
								}
							}
							if (!move.isEmpty()) {
								System.out.println(newCycle.toString());
								return;
							}
							cycles.add(newCycle);
						}
					}
				}
			}
		}
	}

	private void handleDisLoop(XCycle cycle, Move move) {
		this.addChanges(cycle.board, cycle.start, cycle.note, move);
	}

	private void handleConLoop(XCycle cycle, List<Cell> cells, Move move) {
		for (Cell cell : cells) {
			if (!cycle.chain.contains(cell)) {
				boolean zero = false;
				boolean one = false;
				for (int i = 1; i < cycle.chain.size(); i++) {
					if (cycle.chain.get(i).canSee(cell)) {
						if (i % 2 == 0) {
							zero = true;
						} else {
							one = true;
						}
					}
				}
				if (zero && one) {
					move.addChange(new NoteChange(cell, cycle.note));
				}
			}
		}
	}

	@Override
	public int getDifficulty() {
		return 125;
	}

}
