package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.ClaimingLockSolver;

public class ClaimingLockSolverTest extends SolverTest {
	
	@Test
	public void testPointingLockSolver() {
		Move move = this.getMove("resources/ClaimingLockSolverTest.txt", new ClaimingLockSolver());
		
		assertNotNull(move);
		assertEquals(6, move.changeList.size());
		
		int[] rows = {0, 0, 1, 1, 2, 2};
		int[] columns = {3, 4, 3, 4, 3, 4};
		int[] notes = {4, 4, 4, 4, 4, 4};
		for (int i = 0; i < 6; i++) {
			assertTrue(this.checkNoteChange(move.changeList.get(i), rows[i], columns[i], notes[i]));
		}
	}

}
