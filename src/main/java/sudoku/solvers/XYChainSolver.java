package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.java.sudoku.Utilities;
import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Change;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class XYChainSolver extends Solver {

	@Override
	public String getName() {
		return "XY-Chain";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		List<Cell> bivalueCells = new ArrayList<Cell>();
		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				if (cell.getNumNotes() == 2) {
					bivalueCells.add(cell);
				}
			}
		}

		Queue<XYChain> chains = new LinkedList<>();
		for (Cell cell : bivalueCells) {
			chains.add(new XYChain(cell, board));
		}

		XYChain bestChain = null;
		List<Change> bestChanges = new ArrayList<>();
		while (!chains.isEmpty()) {
			XYChain chain = chains.poll();

			if (bestChain != null && bestChain.size() == chain.size()) {
				continue;
			}

			for (Cell cell : bivalueCells) {
				XYChain newChain = chain.tryAddCell(cell);
				if (newChain != null) {
					List<Change> changes = this.tryPinch(newChain);
					if (!changes.isEmpty()) {
						if (changes.size() > bestChanges.size()) {
							bestChanges = changes;
							bestChain = newChain;
						}
					} else if (bestChanges.isEmpty()) {
						chains.add(newChain);
					}
				}
			}
		}

		if (!bestChanges.isEmpty()) {
			for (Change change : bestChanges) {
				move.addChange(change);
			}

			move.description = this.getName() + ": " + bestChain.toString();
		}
	}

	private List<Change> tryPinch(XYChain chain) {
		List<Change> changes = new ArrayList<>();

		Cell base = chain.chain.get(1);
		Cell stem = chain.chain.get(chain.size() - 2);

		int overlap1 = chain.start.getIntNotes() - (base.getIntNotes() & chain.start.getIntNotes());
		int overlap2 = chain.end.getIntNotes() - (stem.getIntNotes() & chain.end.getIntNotes());

		if (overlap1 == overlap2) {
			int toRemove = Utilities.unfold(overlap1);

			for (Cell cell : chain.getCandidates()) {
				if (cell.notes[toRemove]) {
					if (cell.canSee(chain.start) && cell.canSee(chain.end) && !chain.chain.contains(cell)) {
						changes.add(new NoteChange(cell, toRemove));
					}
				}
			}
		}

		return changes;
	}

	@Override
	public int getDifficulty() {
		return 150;
	}

}
