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
		assertEquals(9, move.changeList.size());

		int[] rows = { 0, 0, 1, 3, 4, 5, 6, 7, 8 };
		int[] columns = { 1, 3, 0, 5, 0, 6, 2, 4, 8 };
		int[] notes = { 5, 5, 5, 5, 5, 5, 5, 5, 5 };

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
		assertEquals(1, move.changeList.size());

		int[] rows = { 1 };
		int[] columns = { 4 };
		int[] notes = { 3 };

		for (int i = 0; i < rows.length; i++) {
			if (!this.containsNoteChange(move, rows[i], columns[i], notes[i])) {
				fail("Did not contain change " + i);
			}
		}
	}

}
