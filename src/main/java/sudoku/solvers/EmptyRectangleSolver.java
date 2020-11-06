package main.java.sudoku.solvers;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;

public class EmptyRectangleSolver extends Solver {

	@Override
	public String getName() {
		return "Empty Rectangle";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		for (int note = 1; note <= 9; note++) {
			List<Cell> ERIs = this.getERIs(board, note);
			List<Cell[]> bivalueChains = this.getBivalueChains(board, note);

			for (Cell ERI : ERIs) {
				for (Cell[] chain : bivalueChains) {
					if (ERI.box == chain[0].box || ERI.box == chain[1].box) {
						continue;
					}
					
					int[] pos = new int[2];
					if (ERI.canSee(chain[0])) {
						pos = this.getPinch(ERI, chain[0], chain[1]);
					} else if (ERI.canSee(chain[1])) {
						pos = this.getPinch(ERI, chain[1], chain[0]);
					} else {
						continue;
					}

					Cell cell = board.rows[pos[0]][pos[1]];
					if (cell.notes[note]) {
						String desc = "Empty Rectangle on " + note;
						desc += "\n\tERI: " + ERI.coordString();
						desc += "\n\tPair: " + chain[0].coordString() + chain[1].coordString();
						desc += "\n\tRemoves " + note + " from " + cell.coordString();
						move.description = desc;
						move.addChange(new NoteChange(cell, note));
						return;
					}
				}
			}
		}

		return;
	}

	private List<Cell> getERIs(Board board, int note) {
		List<Cell> ERIs = new ArrayList<>();

		for (int xoff = 0; xoff < 9; xoff += 3) {
			for (int yoff = 0; yoff < 9; yoff += 3) {
				boolean found = false;
				
				int fillCount = 0;
				int noteCount = 0;
				for (int row = yoff; row < yoff + 3; row++) {
					for (int column = xoff; column < xoff + 3; column++) {
						if (board.rows[row][column].value == note) {
							fillCount++;
						}
						if (board.rows[row][column].notes[note]) {
							noteCount++;
						}
					}
				}
				if (fillCount > 0 || noteCount < 3) {
					continue;
				}

				for (int row = yoff; row < yoff + 3; row++) {
					for (int column = xoff; column < xoff + 3; column++) {
						boolean good = true;
						int rowGood = -1;
						int colGood = -1;
						for (int i = yoff; i < yoff + 3; i++) {
							for (int j = xoff; j < xoff + 3; j++) {
								Cell cell = board.rows[i][j];
								if (cell.notes[note]) {
									if (row != i && column != j) {
										good = false;
										break;
									}
									
									if (row != i) {
										colGood = j;
									}
									if (column != j) {
										rowGood = i;
									}
								}
							}

							if (!good) {
								break;
							}
						}

						if (good && rowGood >= 0 && colGood >= 0) {
							ERIs.add(board.rows[rowGood][colGood]);
							found = true;
							break;
						}
					}

					if (found) {
						break;
					}
				}
			}
		}

		return ERIs;
	}

	private List<Cell[]> getBivalueChains(Board board, int note) {
		List<Cell[]> bivalueChains = new ArrayList<>();

		for (int i = 0; i < board.rows.length; i++) {
			Cell[] rowChain = getBivalueChainsHelper(board.rows[i], note);
			if (rowChain[1] != null) {
				bivalueChains.add(rowChain);
			}
			Cell[] columnChain = getBivalueChainsHelper(board.columns[i], note);
			if (columnChain[1] != null) {
				bivalueChains.add(columnChain);
			}
		}

		return bivalueChains;
	}

	private Cell[] getBivalueChainsHelper(Cell[] house, int note) {
		Cell[] chain = new Cell[2];

		for (Cell cell : house) {
			if (cell.getNumNotes() == 2 && cell.notes[note]) {
				if (chain[0] == null) {
					chain[0] = cell;
				} else if (chain[1] == null) {
					if (chain[0].box != cell.box && chain[0].getIntNotes() == cell.getIntNotes()) {
						chain[1] = cell;
						break;
					}
				}
			}
		}

		return chain;
	}

	private int[] getPinch(Cell ERI, Cell link, Cell pincer) {
		int[] pos = new int[2];

		if (ERI.row == link.row) {
			pos[0] = pincer.row;
			pos[1] = ERI.column;
		} else {
			pos[0] = ERI.row;
			pos[1] = pincer.column;
		}

		return pos;
	}

	@Override
	public int getDifficulty() {
		return 400;
	}

}
