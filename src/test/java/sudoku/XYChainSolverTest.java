package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.XYChainSolver;

public class XYChainSolverTest extends SolverTest {

	@Test
	public void testXYChainSolver() {
		Move move = this.getMove("resources/test/XYChain", new XYChainSolver());

		assertNotNull(move);
		assertEquals(3, move.changeList.size());

		int[] rows = { 0, 2, 2 };
		int[] columns = { 2, 6, 8 };
		int[] values = { 5, 5, 5 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], values[i])) {
				fail("Did not contain change " + i);
			}
		}
	}

}
