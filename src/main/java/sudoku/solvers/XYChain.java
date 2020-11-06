package main.java.sudoku.solvers;

import java.util.HashSet;
import java.util.Set;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;

public class XYChain extends Chain {
	
	private Set<Cell> candidates;
	
	public XYChain(Cell start, Board board) {
		super(start);
		
		this.candidates = new HashSet<>();
		for (Cell cell : board.rows[start.row]) {
			this.candidates.add(cell);
		}
		for (int i = 0; i < board.rows.length; i++) {
			this.candidates.add(board.rows[start.row][i]);
			this.candidates.add(board.columns[start.column][i]);
			this.candidates.add(board.boxes[start.box][i]);
		}
	}
	
	public XYChain(XYChain chain, Cell cell) {
		super(chain.chain, cell);
		
		this.candidates = new HashSet<>();
		this.candidates.addAll(chain.candidates);
	}
	
	public XYChain tryAddCell(Cell cell) {
		if (!this.chain.contains(cell)) {
			if (this.end.canSee(cell)) {
				int endValue = this.end.getIntNotes();
				int cellValue = cell.getIntNotes();
				if ((cellValue & endValue) > 0) {
					if (this.size() <= 2) {
						return new XYChain(this, cell);
					} else {
						Cell stem = this.chain.get(this.size() - 2);
						if ((stem.getIntNotes() | cellValue) != 0) {
							return new XYChain(this, cell);
						}
					}
				}
			}
		}
		return null;
	}
	
	public Set<Cell> getCandidates() {
		return this.candidates;
	}
	
	public String toString() {
		String s = start.coordString();
		for (int i = 1; i < chain.size(); i++) {
			s += " <--> " + chain.get(i).coordString();
		}
		return s;
	}

}
