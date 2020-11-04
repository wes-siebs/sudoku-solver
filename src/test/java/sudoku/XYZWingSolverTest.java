package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.XYZWingSolver;

public class XYZWingSolverTest extends SolverTest {

	@Test
	public void testUniqueRectangleSolver() {
		Move move = this.getMove("resources/XYZWingTest.txt", new XYZWingSolver());

		assertNotNull(move);
		assertEquals(1, move.changeList.size());

		int[] rows = { 8 };
		int[] columns = { 0 };
		int[] notes = { 3 };
		for (int i = 0; i < rows.length; i++) {
			assertTrue(this.checkNoteChange(move.changeList.get(i), rows[i], columns[i], notes[i]));
		}
	}

}
