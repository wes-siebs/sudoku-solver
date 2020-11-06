package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
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
		List<Cell> bestPinch = new ArrayList<>();
		while (!chains.isEmpty()) {
			XYChain chain = chains.poll();

			if (bestChain != null && bestChain.size() == chain.size()) {
				continue;
			}

			for (Cell cell : bivalueCells) {
				XYChain newChain = chain.tryAddCell(cell);
				if (newChain != null) {
					List<Cell> pinches = newChain.getPinches();
					if (!pinches.isEmpty()) {
						if (pinches.size() > bestPinch.size()) {
							bestPinch = pinches;
							bestChain = newChain;
						}
					} else if (bestPinch.isEmpty()) {
						chains.add(newChain);
					}
				}
			}
		}

		if (!bestPinch.isEmpty()) {
			for (Cell cell : bestPinch) {
				move.addChange(new NoteChange(cell, bestChain.startNote));
			}

			move.description = this.getName() + ": " + bestChain.toString();
		}
	}

	@Override
	public int getDifficulty() {
		return 150;
	}

}
