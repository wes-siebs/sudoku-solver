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
		this(PuzzleLoader.loadPuzzle(filename), solvers);
	}

	public Puzzle(int[][] values, Solver[] solvers) {
		this.board = new Board(values);
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
		while (!this.isSolved()) {
			boolean madeMove = false;
			
			for (Solver solver : this.solvers) {
				Move nextMove = solver.getNextMove(this.board);
				
				if (!nextMove.isEmpty()) {
					System.out.println(solver.getName());
					nextMove.apply();
					
					this.undoMoves.push(nextMove);
					this.difficulty = Math.max(this.difficulty, solver.getDifficulty());
					
					madeMove = true;
					break;
				}
			}
			
			if (!madeMove) {
				break;
			}
		}
		
		if (this.isSolved()) {
			System.out.println("Puzzle was solved.");
		} else {
			System.out.println("Puzzle was not solved.");
		}
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
