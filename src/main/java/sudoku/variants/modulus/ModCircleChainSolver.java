package main.java.sudoku.variants.modulus;

import java.util.ArrayList;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Move;
import main.java.sudoku.components.NoteChange;
import main.java.sudoku.solvers.Solver;

public class ModCircleChainSolver extends Solver {

	@Override
	public String getName() {
		return "Modulus Circle Chain";
	}

	@Override
	protected void makeNextMove(Move move, Board board) {
		if (!(board instanceof ModBoard)) {
			return;
		}

		ModBoard modBoard = (ModBoard) board;

		List<List<ModCircle>> chains = makeChains(modBoard.modCircles);
		
		for (List<ModCircle> chain : chains) {
			checkChain(move, chain);
			
			if (!move.isEmpty()) {
				move.description = "Modulus Circle Chain on:";
				for (ModCircle circle : chain) {
					move.description += "\n\t" + circle.toString();
				}
				break;
			}
		}
	}

	public List<List<ModCircle>> makeChains(List<ModCircle> modCircles) {
		List<List<ModCircle>> chains = new ArrayList<>();

		List<ModCircle> circles = new ArrayList<>(modCircles);
		while (!circles.isEmpty()) {
			List<ModCircle> chain = new ArrayList<>();
			
			chain.add(circles.remove(0));
			
			int size = 0;
			while (chain.size() != size) {
				size = chain.size();
				
				for (int i = 0; i < circles.size(); i++) {
					ModCircle circle = circles.get(i);
					
					for (int j = 0; j < chain.size(); j++) {
						if (touches(circle, chain.get(j))) {
							chain.add(circle);
							circles.remove(i--);
							break;
						}
					}
				}
			}
			
			chains.add(chain);
		}

		return chains;
	}
	
	public boolean touches(ModCircle c1, ModCircle c2) {
		if (c1.c1 == c2.c1 || c1.c1 == c2.c2) {
			return true;
		}
		if (c1.c2 == c2.c1 || c1.c2 == c2.c2) {
			return true;
		}
		return false;
	}
	
	public void checkChain(Move move, List<ModCircle> chain) {
		List<Cell> cells = new ArrayList<>();
		
		for (ModCircle circle : chain) {
			if (!cells.contains(circle.c1)) {
				cells.add(circle.c1);
			}
			if (!cells.contains(circle.c2)) {
				cells.add(circle.c2);
			}
		}
		
		boolean[][] possibilities = new boolean[cells.size()][10];
		boolean[][] checked = new boolean[cells.size()][10];
		int[] start = new int[cells.size()];
		int[] values = new int[cells.size()];
		for (int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			
			if (cell.getValue() == 0) {
				boolean set = false;
				for (int j = 0; j < 10; j++) {
					if (!set && cell.getNote(j)) {
						start[i] = j;
						set = true;
					}
					possibilities[i][j] = cell.getNote(j);
				}
			} else {
				start[i] = cell.getValue();
				possibilities[i][cell.getValue()] = true;
			}
			
			values[i] = start[i];
		}
		
		while (true) {
			if (checkArrangement(chain, cells, values)) {
				for (int i = 0; i < cells.size(); i++) {
					checked[i][values[i]] = true;
				}
			}
			
			boolean set = false;
			for (int i = 0; i < cells.size(); i++) {
				values[i]++;
				while (values[i] < 10 && !possibilities[i][values[i]]) {
					values[i]++;
				}
				if (values[i] == 10) {
					values[i] = start[i];
				} else {
					set = true;
					break;
				}
			}
			
			if (!set) {
				break;
			}
		}
		
		for (int i = 0; i < cells.size(); i++) {
			for (int j = 0; j < 10; j++) {
				if (possibilities[i][j] && !checked[i][j]) {
					move.addChange(new NoteChange(cells.get(i), j));
				}
			}
		}
	}
	
	public boolean checkArrangement(List<ModCircle> chain, List<Cell> cells, int[] values) {
		// check circles
		for (ModCircle circle : chain) {
			int v1 = values[cells.indexOf(circle.c1)] % circle.mod;
			int v2 = values[cells.indexOf(circle.c2)] % circle.mod;
			if (v1 != v2) {
				return false;
			}
		}
		
		// check cells
		for (int i = 0; i < cells.size(); i++) {
			for (int j = i + 1; j < cells.size(); j++) {
				if (cells.get(i).canSee(cells.get(j)) && values[i] == values[j]) {
					return false;
				}
			}
		}
		
		return true;
	}

	@Override
	public int getDifficulty() {
		return -1;
	}

}
