package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Cell;

public class Chain {

	public Cell start;
	public Cell end;
	public List<Cell> chain;

	public Chain(Cell start) {
		this.start = start;
		this.end = start;
		this.chain = new ArrayList<>();
		this.chain.add(start);
	}
	
	public Chain(List<Cell> cells, Cell end) {
		this.start = cells.get(0);
		this.end = end;
		this.chain = new ArrayList<>();
		this.chain.addAll(cells);
		this.chain.add(end);
	}
	
	public int size() {
		return this.chain.size();
	}

}
