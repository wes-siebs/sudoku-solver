package main.java.sudoku.solvers;

public class SolverList {

	public static final Solver[] solverList = {
			new OpenSingleSolver(),
			new NakedSingleSolver(),
			new HiddenSingleSolver(),
			new NakedPairSolver(),
			new NakedTripleSolver(),
			new NakedQuadSolver(),
			new HiddenPairSolver(),
			new HiddenTripleSolver(),
			new HiddenQuadSolver(),
			new XWingSolver(),
			new SwordfishSolver(),
			new XYWingSolver(),
			new JellyfishSolver(),
	};

}
