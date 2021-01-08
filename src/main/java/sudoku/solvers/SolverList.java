package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

public class SolverList {
	
	private static final Solver[] solverList = {
			new ClaimingLockSolver(),
			new EmptyRectangleSolver(),
			new HiddenPairSolver(),
			new HiddenQuadSolver(),
			new HiddenSingleSolver(),
			new HiddenTripleSolver(),
			new JellyfishSolver(),
			new NakedPairSolver(),
			new NakedQuadSolver(),
			new NakedSingleSolver(),
			new NakedTripleSolver(),
			new PointingLockSolver(),
			new SimpleColoringSolver(),
			new SwordfishSolver(),
			new UniqueRectangleSolver(),
			new WXYZWingSolver(),
			new XCycleSolver(),
			new XWingSolver(),
			new XYChainSolver(),
			new XYWingSolver(),
			new XYZWingSolver()
	};

	private static final List<Solver> variantSolvers = new ArrayList<>();

	public final Solver[] solvers;

	private static SolverList instance;

	private SolverList() {
		int numSolvers = solverList.length + variantSolvers.size();

		this.solvers = new Solver[numSolvers];

		boolean[] used = new boolean[numSolvers];
		for (int i = 0; i < numSolvers; i++) {

			int minj = -1;
			int minval = Integer.MAX_VALUE;

			for (int j = 0; j < numSolvers; j++) {
				Solver solver;
				if (j < solverList.length) {
					solver = solverList[j];
				} else {
					solver = variantSolvers.get(j - solverList.length);
				}

				if (!used[j] && solver.getDifficulty() < minval) {
					minj = j;
					minval = solver.getDifficulty();
				}
			}

			used[minj] = true;
			if (minj < solverList.length) {
				this.solvers[i] = solverList[minj];
			} else {
				this.solvers[i] = variantSolvers.get(minj - solverList.length);
			}
		}
	}

	public static void addVariantSolvers(List<Solver> solvers) {
		if (instance == null) {
			variantSolvers.addAll(solvers);
		} else {
			System.err.println("Cannot load solver after calling getInstance().");
		}
	}

	public static SolverList getInstance() {
		if (instance == null) {
			instance = new SolverList();
		}

		return instance;
	}

}
