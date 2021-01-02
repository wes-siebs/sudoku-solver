package main.java.sudoku.solvers;

import main.java.sudoku.variants.ModCircleSolver;

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
			new XYZWingSolver(),
			new ModCircleSolver()
	};
	
	public final Solver[] solvers;
	
	private static SolverList instance;
	
	private SolverList() {
		this.solvers = new Solver[solverList.length];
		
		boolean[] used = new boolean[solverList.length];
		for (int i = 0; i < this.solvers.length; i++) {
			
			int minj = -1;
			int minval = Integer.MAX_VALUE;
			
			for (int j = 0; j < solverList.length; j++) {
				if (!used[j] && solverList[j].getDifficulty() < minval) {
					minj = j;
					minval = solverList[j].getDifficulty();
				}
			}
			
			used[minj] = true;
			this.solvers[i] = solverList[minj];
		}
	}
	
	public static SolverList getInstance() {
		if (instance == null) {
			instance = new SolverList();
		}
		
		return instance;
	}

}
