package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.WXYZWingSolver;

public class WXYZWingSolverTest extends SolverTest {

	@Test
	public void testWXYZWingSolver() {
		Move move = this.getMove("resources/test/WXYZWing", new WXYZWingSolver());

		assertNotNull(move);
		assertEquals(1, move.changeList.size());

		int[] rows = { 3 };
		int[] columns = { 1 };
		int[] values = { 9 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], values[i])) {
				fail("Did not contain change " + i);
			}
		}
	}

}
