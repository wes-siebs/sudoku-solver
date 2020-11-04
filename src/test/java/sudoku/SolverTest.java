package test.java.sudoku;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Change;
import main.java.sudoku.components.FillChange;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;
import main.java.sudoku.components.PuzzleLoader;
import main.java.sudoku.solvers.Solver;

public class SolverTest {

	public Move[] getMoves(String filename, Solver solver, int num) {
		Board board = new Board(PuzzleLoader.loadPuzzle(filename));
		Move[] moves = new Move[num];
		for (int i = 0; i < num; i++) {
			moves[i] = solver.getNextMove(board);
			moves[i].apply();
		}
		return moves;
	}

	public Move getMove(String filename, Solver solver) {
		return this.getMoves(filename, solver, 1)[0];
	}

	public boolean checkFillChange(Change change, int row, int column, int value) {
		if (change instanceof FillChange) {
			FillChange fillChange = (FillChange) change;
			if (fillChange.cell.row != row) {
				return false;
			}
			if (fillChange.cell.column != column) {
				return false;
			}
			if (fillChange.newValue != value) {
				return false;
			}
			return true;
		}
		return false;
	}

	public void checkNoteChange(Change change, int row, int column, int note) {
		assertTrue(change instanceof NoteChange);
		NoteChange noteChange = (NoteChange) change;
		assertEquals(row, noteChange.cell.row);
		assertEquals(column, noteChange.cell.column);
		assertEquals(note, noteChange.note);
	}

}
