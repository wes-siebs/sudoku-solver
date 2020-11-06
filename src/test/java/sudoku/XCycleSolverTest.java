package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.XCycleSolver;

public class XCycleSolverTest extends SolverTest {

	@Test
	public void testRule1() {
		Move move = this.getMove("resources/test/XCycle1", new XCycleSolver());

		assertNotNull(move);
		assertEquals(1, move.changeList.size());

		int[] rows = { 3 };
		int[] columns = { 4 };
		int[] values = { 8 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], values[i])) {
				fail("Did not contain change " + i);
			}
		}
	}

	@Test
	public void testRule2() {
		Move move = this.getMove("resources/test/XCycle2", new XCycleSolver());

		assertNotNull(move);
		assertEquals(2, move.changeList.size());

		int[] rows = { 5, 6 };
		int[] columns = { 0, 2 };
		int[] values = { 1, 1 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], values[i])) {
				fail("Did not contain change " + i);
			}
		}
	}

}
