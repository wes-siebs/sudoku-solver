package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Move;
import main.java.sudoku.solvers.UniqueRectangleSolver;

public class UniqueRectangleSolverTest extends SolverTest {

	@Test
	public void testUniqueRectangleSolver() {
		Move move = this.getMove("resources/UniqueRectangleTest.txt", new UniqueRectangleSolver());

		assertNotNull(move);
		assertEquals(2, move.changeList.size());

		int[] rows = { 1, 1 };
		int[] columns = { 8, 8 };
		int[] notes = { 3, 5 };
		for (int i = 0; i < rows.length; i++) {
			assertTrue(this.checkNoteChange(move.changeList.get(i), rows[i], columns[i], notes[i]));
		}
	}
	
	@Test
	public void testUniqueRectangleSolver2() {
		Move move = this.getMove("resources/UniqueRectangleTest2.txt", new UniqueRectangleSolver());

		assertNotNull(move);
		assertEquals(0, move.changeList.size());
	}

}
