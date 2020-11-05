package main.java.sudoku.components;

import java.util.Stack;

import main.java.sudoku.solvers.Solver;

public class Puzzle {

	private Board board;
	private Solver[] solvers;
	private Stack<Move> undoMoves;
	private Stack<Move> redoMoves;
	private int difficulty;

	public Puzzle(String filename, Solver[] solvers) {
		this(BoardLoader.loadBoard(filename), solvers);
	}

	public Puzzle(Board board, Solver[] solvers) {
		this.board = board;
		this.solvers = solvers;

		this.undoMoves = new Stack<Move>();
		this.redoMoves = new Stack<Move>();
		this.difficulty = -1;
	}

	public Board getBoard() {
		return this.board;
	}

	public boolean isSolved() {
		for (Cell[] row : this.board.rows) {
			for (Cell cell : row) {
				if (cell.value == 0) {
					return false;
				}
			}
		}

		return true;
	}

	public void solve() {
		Move nextMove;
		while ((nextMove = takeStep()) != null) {
			nextMove.apply();
			this.undoMoves.push(nextMove);
		}

		if (this.isSolved()) {
			System.out.println("Puzzle was solved.");
			System.out.println(difficulty + " stars");
		} else {
			System.out.println("Puzzle was not solved.");
		}
	}

	public Move takeStep() {
		for (Solver solver : this.solvers) {
			Move nextMove = solver.getNextMove(this.board);

			if (!nextMove.isEmpty()) {
				System.out.println(solver.getName());
				this.difficulty = Math.max(this.difficulty, solver.getDifficulty());
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
}
