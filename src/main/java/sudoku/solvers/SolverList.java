package main.java.sudoku.solvers;

public class SolverList {

	public static final Solver[] solverList = {
			new OpenSingleSolver(),
			new NakedSingleSolver(),
			new HiddenSingleSolver(),
			new NakedPairSolver(),
			new NakedTripleSolver(),
			new NakedQuadSolver()
	};

}
