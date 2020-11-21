package main.java.sudoku.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import main.java.sudoku.solvers.Solver;

public class Puzzle {

	private Board board;
	private Solver[] solvers;
	private Stack<Move> undoMoves;
	private Stack<Move> redoMoves;
	private List<Move> moves;
	private int difficulty;

	public Puzzle(String filename, Solver[] solvers) {
		this(BoardLoader.loadBoard(filename), solvers);
	}

	public Puzzle(Board board, Solver[] solvers) {
		this.board = board;
		this.solvers = solvers;

		this.undoMoves = new Stack<Move>();
		this.redoMoves = new Stack<Move>();
		this.moves = new ArrayList<Move>();
		this.difficulty = -1;
	}

	public Board getBoard() {
		return this.board;
	}

	public boolean isSolved() {
		for (Cell[] row : this.board.rows) {
			for (Cell cell : row) {
				if (cell.getValue() == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public void solve() {
		if (!this.moves.isEmpty()) {
			while (this.canRedo()) {
				this.redo();
			}
		} else {
			Move nextMove;
			while ((nextMove = takeStep()) != null) {
				nextMove.apply();
				this.undoMoves.push(nextMove);
				this.moves.add(nextMove);
			}
			
			if (this.isSolved()) {
				System.out.println("Puzzle was solved.");
				System.out.println(difficulty + " points");
			} else {
				System.out.println("Puzzle was not solved.");
			}
		}
	}

	public Move takeStep() {
		for (Solver solver : this.solvers) {
			Move nextMove = solver.getNextMove(this.board);

			if (!nextMove.isEmpty()) {
				if (nextMove.description.isEmpty()) {
					System.out.println(solver.getName());
				} else {
					System.out.println(nextMove.description);
				}
				this.difficulty += solver.getDifficulty();
				return nextMove;
			}
		}

		return null;
	}

	public boolean canUndo() {
		return !this.undoMoves.isEmpty();
	}

	public void undo() {
		if (this.canUndo()) {
			Move move = this.undoMoves.pop();
			move.unapply();
			this.redoMoves.push(move);
		}
	}

	public boolean canRedo() {
		return !this.redoMoves.isEmpty();
	}

	public void redo() {
		if (this.canRedo()) {
			Move move = this.redoMoves.pop();
			move.apply();
			this.undoMoves.push(move);
		}
	}

	public int difficulty() {
		return this.difficulty;
	}

	public int[] getChart() {
		int[] vals = new int[this.moves.size()];
		
		for (int i = 0; i < vals.length; i++) {
			vals[i] = this.moves.get(i).difficulty;
		}
		
		return vals;
	}
	
	public double getProgress() {
		if (this.moves.size() == this.undoMoves.size()) {
			return 1;
		}
		
		return 1.0 * this.undoMoves.size() / this.moves.size();
	}
}
