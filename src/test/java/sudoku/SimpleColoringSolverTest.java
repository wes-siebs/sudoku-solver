package test.java.sudoku;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Change;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;
import main.java.sudoku.solvers.SimpleColoringSolver;

public class SimpleColoringSolverTest extends SolverTest {

	@Test
	public void testRule2() {
		Move[] moves = this.getMoves("resources/SimpleColoringTest2.txt", new SimpleColoringSolver(), 3);
		Move move = moves[2];

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
		Board board = new Board(new int[9][9]);
		int[] setR = { 0, 0, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 6, 6, 8, 8, 8, 8 };
		int[] setC = { 5, 6, 0, 3, 1, 8, 0, 1, 4, 0, 3, 5, 7, 8, 3, 7, 3, 4, 6, 7 };
		for (Cell[] row : board.rows) {
			for (Cell cell : row) {
				for (int i = 0; i < cell.possibilities.length; i++) {
					cell.possibilities[i] = false;
				}
			}
		}
		for (int i = 0; i < setR.length; i++) {
			board.rows[setR[i]][setC[i]].possibilities[1] = true;
		}
		
		Move move = new SimpleColoringSolver().getNextMove(board);

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
	
	public boolean containsNoteChange(Move move, int row, int column, int note) {
		for (Change change : move.changeList) {
			if (change instanceof NoteChange) {
				NoteChange nchange = (NoteChange) change;
				
				if (nchange.cell.row != row) {
					continue;
				} else if (nchange.cell.column != column) {
					continue;
				} else if (nchange.note != note) {
					continue;
				} else {
					return true;
				}
			}
		}
		
		return false;
	}

}
