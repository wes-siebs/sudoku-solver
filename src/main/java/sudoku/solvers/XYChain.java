package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;

public class XYChain extends Chain {
	
	private Set<Cell> candidates;
	public int startNote = 0;
	private int linkNote = 0;
	private int endNote = 0;
	
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
	
	public XYChain(XYChain chain, Cell cell, int startNote, int linkNote, int endNote) {
		super(chain.chain, cell);
		
		this.candidates = new HashSet<>();
		this.candidates.addAll(chain.candidates);
		
		this.startNote = startNote;
		this.linkNote = linkNote;
		this.endNote = endNote;
	}
	
	public XYChain tryAddCell(Cell cell) {
		if (!this.chain.contains(cell)) {
			if (this.end.canSee(cell)) {
				if (this.size() == 1) {
					int startNote = 0;
					int linkNote = 0;
					int endNote = 0;
					for (int i = 1; i <= 9; i++) {
						if (this.start.getNote(i)) {
							if (cell.getNote(i)) {
								linkNote = i;
							} else {
								startNote = i;
							}
						} else {
							if (cell.getNote(i)) {
								endNote = i;
							}
						}
					}
					
					if (startNote > 0 && linkNote > 0 && endNote > 0) {
						return new XYChain(this, cell, startNote, linkNote, endNote);
					}
				} else {
					if (cell.getNote(this.endNote) && !cell.getNote(this.linkNote)) {
						int newEnd = 0;
						for (int note = 1; note <= 9; note++) {
							if (cell.getNote(note) && note != this.endNote) {
								newEnd = note;
								break;
							}
						}
						
						return new XYChain(this, cell, this.startNote, this.endNote, newEnd);
					}
				}
			}
		}
		return null;
	}
	
	public List<Cell> getPinches() {
		List<Cell> cells = new ArrayList<>();
		if (this.startNote != this.endNote) {
			return cells;
		} else {
			for (Cell cell : this.candidates) {
				if (!this.chain.contains(cell) && cell.getNote(this.startNote) && this.end.canSee(cell)) {
					cells.add(cell);
				}
			}
			
			return cells;
		}
	}
	
	public String toString() {
		String s = start.coordString();
		for (int i = 1; i < chain.size(); i++) {
			s += " <--> " + chain.get(i).coordString();
		}
		return s;
	}

}
