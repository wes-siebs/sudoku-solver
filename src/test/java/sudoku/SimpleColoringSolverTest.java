package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.SimpleColoringSolver;

public class SimpleColoringSolverTest extends SolverTest {

	@Test
	public void testRule2() {
		Move move = this.getMove("resources/test/SimpleColoring2", new SimpleColoringSolver());

		assertNotNull(move);
		assertEquals(5, move.changeList.size());

		int[] rows = { 0, 0, 2, 4, 5 };
		int[] columns = { 2, 4, 1, 1, 3 };
		int[] notes = { 9, 9, 9, 9, 9, 9 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], notes[i])) {
				fail("Did not contain change " + i);
			}
		}
	}

	@Test
	public void testRule4() {
		Move move = this.getMove("resources/test/SimpleColoring4", new SimpleColoringSolver());

		assertNotNull(move);
		assertEquals(3, move.changeList.size());

		int[] rows = { 3, 4, 8 };
		int[] columns = { 4, 0, 3 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], 1)) {
				fail("Did not contain change " + i);
			}
		}
	}

}
