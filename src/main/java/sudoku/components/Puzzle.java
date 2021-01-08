package main.java.sudoku.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import main.java.sudoku.solvers.Solver;
import main.java.sudoku.variants.modulus.ModBoard;
import main.java.sudoku.variants.modulus.ModCircle;

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

	public boolean isSolvable() {
		if (!check(this.board.rows)) {
			return false;
		}
		if (!check(this.board.columns)) {
			return false;
		}
		if (!check(this.board.boxes)) {
			return false;
		}

		for (Cell[] row : this.board.rows) {
			for (Cell cell : row) {
				if (cell.getValue() == 0) {
					boolean good = false;
					for (int i = 1; i < 10; i++) {
						if (cell.getNote(i)) {
							good = true;
							break;
						}
					}
					if (!good) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private boolean check(Cell[][] houses) {
		for (Cell[] house : houses) {
			int[] numNote = new int[10];
			int[] numValue = new int[10];

			for (Cell cell : house) {
				if (cell.getValue() == 0) {
					for (int i = 0; i < 10; i++) {
						if (cell.getNote(i)) {
							numNote[i]++;
						}
					}
				} else {
					numValue[cell.getValue()]++;
					numNote[cell.getValue()]++;
				}
			}

			for (int i = 1; i < 10; i++) {
				if (numNote[i] == 0 || numValue[i] > 1) {
					return false;
				}
			}
		}

		return true;
	}

	public void solve() {
		if (this.isSolved()) {
			System.out.println("Puzzle was solved.");
			System.out.println(difficulty + " points");
		} else if (!this.redoMoves.isEmpty()) {
			while (this.canRedo()) {
				this.redo();
			}
		} else if (!this.isSolvable()) {
			System.out.println("Puzzle has no solution.");
		} else {
			Move nextMove = takeStep();
			if (nextMove == null) {
				System.out.println("Puzzle was not solved.");
			} else {
				nextMove.apply();
				this.undoMoves.push(nextMove);
				this.moves.add(nextMove);
				solve();
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

	public String getNote() {
		if (this.redoMoves.size() > 0) {
			return this.redoMoves.get(this.redoMoves.size() - 1).description;
		}
		return "";
	}

	public double getProgress() {
		if (this.moves.size() == this.undoMoves.size()) {
			return 1;
		}

		return 1.0 * this.undoMoves.size() / this.moves.size();
	}

	public void randomize(int numCells, int numCircles) {
		while (this.canUndo()) {
			this.undo();
		}
		this.undoMoves.clear();
		this.redoMoves.clear();
		this.moves.clear();

		if (numCircles != -1) {
			ModBoard modBoard = (ModBoard) this.board;
			modBoard.modCircles.clear();

			while (modBoard.modCircles.size() < numCircles) {
				ModCircle circle;
				int v1 = (int) (Math.random() * 8);
				int v2 = (int) (Math.random() * 9);
				int v3 = (int) (Math.random() * 7 + 2);
				if (Math.random() > 0.5) {
					circle = new ModCircle(this.board.rows[v1][v2], this.board.rows[v1 + 1][v2], v3);
				} else {
					circle = new ModCircle(this.board.rows[v2][v1], this.board.rows[v2][v1 + 1], v3);
				}
				boolean good = true;
				for (ModCircle modCircle : modBoard.modCircles) {
					if (modCircle.c1 == circle.c1 && modCircle.c2 == circle.c2) {
						good = false;
						break;
					}
				}
				if (good) {
					modBoard.modCircles.add(circle);
				}
			}
		}

		this.board.clear();
		for (int i = 0; i < numCells; i++) {
			int r = (int) (Math.random() * 9);
			int c = (int) (Math.random() * 9);
			if (this.board.rows[r][c].getValue() > 0) {
				i--;
			} else {
				int v = (int) (Math.random() * 9 + 1);
				this.board.set(r, c, v);
			}
		}
	}
}
