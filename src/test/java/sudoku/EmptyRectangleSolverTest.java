package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.EmptyRectangleSolver;

public class EmptyRectangleSolverTest extends SolverTest {

	@Test
	public void testXYChainSolver() {
		Move move = this.getMove("resources/test/EmptyRectangle", new EmptyRectangleSolver());

		assertNotNull(move);
		assertEquals(1, move.changeList.size());

		int[] rows = { 3 };
		int[] columns = { 8 };
		int[] values = { 6 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], values[i])) {
				fail("Did not contain change " + i);
			}
		}
	}

}
