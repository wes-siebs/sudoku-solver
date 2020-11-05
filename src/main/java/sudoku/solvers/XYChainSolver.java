package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

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
	public Move getNextMove(Board board) {
		Move nextMove = new Move();
		List<Cell> bivalueCells = new ArrayList<Cell>();
		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				if (cell.getNumNotes() == 2) {
					bivalueCells.add(cell);
				}
			}
		}

		List<Change> changes = new ArrayList<>();
		List<Cell> bestChain = new ArrayList<>();
		for (Cell cell : bivalueCells) {
			List<Cell> chain = new ArrayList<Cell>();
			chain.add(cell);
			this.followChain(chain, bivalueCells, -1, board, nextMove, changes, bestChain);
		}

		for (Change change : changes) {
			nextMove.addChange(change);
		}

		return nextMove;
	}

	private void followChain(List<Cell> chain, List<Cell> cells, int lastOverlap, Board board, Move move,
			List<Change> bestChanges, List<Cell> bestChain) {
		if (bestChain.size() > 0 && chain.size() > bestChain.size()) {
			return;
		}

		Cell link = chain.get(chain.size() - 1);

		for (Cell cell : cells) {
			if (chain.contains(cell)) {
				continue;
			}

			if (cell.canSee(link) && cell != link) {
				int overlap = Utilities.unfold(cell.getIntNotes() & link.getIntNotes());
				if (overlap != 0 && overlap != lastOverlap) {
					List<Cell> newChain = new ArrayList<>();
					newChain.addAll(chain);
					newChain.add(cell);

					if (newChain.size() > 2) {
						List<Change> changes = this.tryPinch(newChain, board, move);
						if (!changes.isEmpty()) {
							if (bestChanges.size() == 0 || bestChain.size() > newChain.size()) {
								bestChain.clear();
								bestChain.addAll(newChain);
								bestChanges.clear();
								bestChanges.addAll(changes);
							}
						}
						if (!move.isEmpty()) {

							return;
						}
					}

					followChain(newChain, cells, overlap, board, move, bestChanges, bestChain);
					if (!move.isEmpty()) {
						return;
					}
				}
			}
		}
	}

	private List<Change> tryPinch(List<Cell> chain, Board board, Move move) {
		List<Change> changes = new ArrayList<>();

		Cell root = chain.get(0);
		Cell base = chain.get(1);
		Cell stem = chain.get(chain.size() - 2);
		Cell leaf = chain.get(chain.size() - 1);

		int overlap1 = root.getIntNotes() - (base.getIntNotes() & root.getIntNotes());
		int overlap2 = leaf.getIntNotes() - (stem.getIntNotes() & leaf.getIntNotes());

		if (overlap1 == overlap2) {
			int toRemove = Utilities.unfold(overlap1);
			for (Cell[] row : board.rows) {
				for (Cell check : row) {
					if (check.possibilities[toRemove]) {
						if (check.canSee(root) && check.canSee(leaf) && !chain.contains(check)) {
							changes.add(new NoteChange(check, toRemove));
						}
					}
				}
			}
		}

		return changes;
	}

	@Override
	public int getDifficulty() {
		return 9;
	}

}
