package test.java.sudoku;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.BoardLoader;
import main.java.sudoku.components.Change;
import main.java.sudoku.components.FillChange;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;
import main.java.sudoku.solvers.Solver;

public class SolverTest {

	public Move getMove(String filename, Solver solver) {
		Board board = BoardLoader.loadBoard(filename);
		return solver.getNextMove(board);
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
	
	public boolean containsFillChange(Move move, int row, int column, int value) {
		for (Change change : move.changeList) {
			if (change instanceof FillChange) {
				FillChange nchange = (FillChange) change;
				
				if (nchange.cell.row != row) {
					continue;
				} else if (nchange.cell.column != column) {
					continue;
				} else if (nchange.newValue != value) {
					continue;
				} else {
					return true;
				}
			}
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
