package main.java.sudoku.variants;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.solvers.Solver;
import main.java.sudoku.solvers.SolverList;
import main.java.sudoku.variants.modulus.ModVariant;

public abstract class Variant {

	protected List<Solver> solvers = new ArrayList<>();

	public static final Variant[] variantList = {
			new ModVariant()
	};

	public void loadSolvers() {
		SolverList.addVariantSolvers(solvers);
	}

	public abstract String getName();

	public abstract VariantDrawer getDrawer();

	public abstract Board loadBoard(Board board, List<String> lines);

}
