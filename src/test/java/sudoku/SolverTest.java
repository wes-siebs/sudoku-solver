package test.java.sudoku;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Change;
import main.java.sudoku.components.FillChange;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;
import main.java.sudoku.components.PuzzleLoader;
import main.java.sudoku.solvers.Solver;

public class SolverTest {
	
	public Move getMove(String filename, Solver solver) {
		Board board = new Board(PuzzleLoader.loadPuzzle(filename));
		return solver.getNextMove(board);
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
	
	public boolean checkNoteChange(Change change, int row, int column, int note) {
		if (change instanceof NoteChange) {
			NoteChange noteChange = (NoteChange) change;
			if (noteChange.cell.row != row) {
				return false;
			}
			if (noteChange.cell.column != column) {
				return false;
			}
			if (noteChange.note != note) {
				return false;
			}
			return true;
		}
		return false;
	}

}
