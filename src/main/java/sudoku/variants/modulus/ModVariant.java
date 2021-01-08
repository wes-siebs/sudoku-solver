package main.java.sudoku.variants.modulus;

import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.variants.Variant;
import main.java.sudoku.variants.VariantDrawer;

public class ModVariant extends Variant {

	public ModVariant() {
		this.solvers.add(new ModCircleSolver());
		this.solvers.add(new ModCircleChainSolver());
	}

	@Override
	public String getName() {
		return "Modulus Variant";
	}

	@Override
	public VariantDrawer getDrawer() {
		return new ModDrawer();
	}

	@Override
	public Board loadBoard(Board board, List<String> lines) {
		ModBoard modBoard = new ModBoard(board.rows, board.columns, board.boxes);
		for (int i = 0; i < 17; i++) {
			String line = lines.get(i).replaceAll(" ", "");
			if (i % 2 == 0) {
				int col = 0;
				for (char c : line.toCharArray()) {
					int val = c - '0';

					if (val > 1) {
						modBoard.modCircles.add(new ModCircle(board.rows[i / 2][col], board.rows[i / 2][col + 1], val));
					}

					col++;
				}
			} else {
				int col = 0;
				for (char c : line.toCharArray()) {
					int val = c - '0';

					if (val > 1) {
						modBoard.modCircles.add(new ModCircle(board.rows[i / 2][col], board.rows[i / 2 + 1][col], val));
					}

					col++;
				}
			}
		}
		return modBoard;
	}

}
