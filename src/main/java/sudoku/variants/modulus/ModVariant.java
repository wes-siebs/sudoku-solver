package main.java.sudoku.variants.modulus;

import main.java.sudoku.components.Board;
import main.java.sudoku.solvers.Solver;
import main.java.sudoku.variants.Variant;
import main.java.sudoku.variants.VariantDrawer;

public class ModVariant implements Variant {

	private final Solver[] solvers = { new ModCircleSolver(), new ModCircleChainSolver() };

	@Override
	public String getName() {
		return "Modulus Variant";
	}

	@Override
	public Solver[] getSolvers() {
		return solvers;
	}

	@Override
	public VariantDrawer getDrawer() {
		return new ModDrawer();
	}

	@Override
	public Board loadBoard() {
		return null;
	}

}
