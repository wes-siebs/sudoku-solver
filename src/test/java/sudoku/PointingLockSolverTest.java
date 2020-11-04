package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.PointingLockSolver;

public class PointingLockSolverTest extends SolverTest {
	
	@Test
	public void testPointingLockSolver() {
		Move move = this.getMove("resources/PointingLockSolverTest.txt", new PointingLockSolver());
		
		assertNotNull(move);
		assertEquals(6, move.changeList.size());
		
		int[] rows = {6, 6, 6, 6, 6, 6};
		int[] columns = {0, 1, 2, 6, 7, 8};
		int[] notes = {1, 1, 1, 1, 1, 1};
		for (int i = 0; i < 6; i++) {
			this.checkNoteChange(move.changeList.get(i), rows[i], columns[i], notes[i]);
		}
	}

}
