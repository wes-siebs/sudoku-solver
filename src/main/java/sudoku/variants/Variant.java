package main.java.sudoku.variants;

import main.java.sudoku.components.Board;
import main.java.sudoku.solvers.Solver;

public interface Variant {

	public String getName();

	public Solver[] getSolvers();

	public VariantDrawer getDrawer();

	public Board loadBoard();

}
