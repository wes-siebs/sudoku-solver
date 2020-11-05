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
		assertEquals(4, move.changeList.size());

		int[] rows = { 2, 2, 6, 6 };
		int[] columns = { 2, 8, 2, 8 };
		int[] values = { 8, 8, 8, 8 };

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
		assertEquals(10, move.changeList.size());

		if (!this.containsFillChange(move, 8, 0, 1)) {
			fail("Did not contain fill change");
		}
		
		int[] rows = { 5, 6, 8, 8, 8, 8 , 8, 8, 8};
		int[] columns = { 0, 2, 0, 0, 0, 7, 0, 0, 0 };
		int[] values = { 1, 1, 1, 3, 9, 1, 1, 1, 1 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], values[i])) {
				fail("Did not contain change " + i);
			}
		}
	}

}
