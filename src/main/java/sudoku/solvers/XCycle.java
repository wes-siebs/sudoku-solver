package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;

public class XCycle {
	
	public Board board;
	public int note;
	public Cell start;
	public Cell end;
	public List<Cell> chain;
	
	public XCycle(Board board, int note, Cell start) {
		this.board = board;
		this.note = note;
		this.start = start;
		this.end = start;
		this.chain = new ArrayList<>();
		this.chain.add(start);
	}
	
	private XCycle(XCycle cycle, Cell end) {
		this.board = cycle.board;
		this.note = cycle.note;
		this.start = cycle.start;
		this.end = end;
		this.chain = new ArrayList<>();
		this.chain.addAll(cycle.chain);
		this.chain.add(end);
	}
	
	public XCycle tryAddCell(Cell cell) {
		if (this.chain.contains(cell)) {
			return null;
		} else if (this.chain.size() % 2 == 0) {
			if (this.hasWeakLink(this.end, cell)) {
				return new XCycle(this, cell);
			}
		} else {
			if (this.hasStrongLink(this.end, cell)) {
				return new XCycle(this, cell);
			}
		}
		
		return null;
	}
	
	public boolean pinches(Cell cell) {
		if (this.chain.size() < 4 || this.chain.size() % 2 == 1) {
			return false;
		} else if (this.chain.contains(cell)) {
			return false;
		} else if (this.start.canSee(cell) && this.end.canSee(cell)) {
			return true;
		}
		return false;
	}
	
	private boolean hasStrongLink(Cell c1, Cell c2) {
		if (c1 == c2 || !c1.notes[this.note] || !c2.notes[this.note]) {
			return false;
		}
		
		if (c1.row == c2.row && this.numNotes(this.board.rows[c1.row], this.note) == 2) {
			return true;
		} else if (c1.column == c2.column && this.numNotes(this.board.columns[c1.column], this.note) == 2) {
			return true;
		} else if (c1.box == c2.box && this.numNotes(this.board.boxes[c1.box], this.note) == 2) {
			return true;
		} else {
			return false;
		}
	}
	
	private int numNotes(Cell[] house, int note) {
		int count = 0;
		for (Cell cell : house) {
			count += cell.notes[note] ? 1 : 0;
		}
		return count;
	}
	
	private boolean hasWeakLink(Cell c1, Cell c2) {
		if (c1 == c2 || !c1.notes[this.note] || !c2.notes[this.note]) {
			return false;
		}
		
		if (c1.row == c2.row) {
			return true;
		} else if (c1.column == c2.column) {
			return true;
		} else if (c1.box == c2.box){
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		String s = this.start.coordString();
		boolean strong = true;
		for (int i = 1; i < this.chain.size(); i++) {
			if (strong) {
				s += " -s-> ";
			} else {
				s += " -w-> ";
			}
			strong = !strong;
			s += this.chain.get(i).coordString();
		}
		return s;
	}

}
