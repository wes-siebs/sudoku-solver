package main.java.sudoku;

public class Utilities {

	public static int getBox(int row, int column, int unit) {
		return (row / unit) * unit + (column / unit);
	}

	public static int getBoxPos(int row, int column, int unit) {
		return (row % unit) * unit + (column % unit);
	}

	public static int unfold(int n) {
		if (n < 2) {
			return 0;
		} else if (n == 2) {
			return 1;
		} else {
			return 1 + unfold(n >> 1);
		}
	}

	public static int[][] nCkTuples(int n, int k, int index) {
		int size = nCk(n, k);

		int[][] tuples = new int[size][k];

		int[] currentTuple = new int[k];
		for (int i = 0; i < k; i++) {
			currentTuple[i] = i;
		}

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < k; j++) {
				tuples[i][j] = currentTuple[j] + index;
			}

			incrementTuple(currentTuple, n, k - 1);
		}

		return tuples;
	}

	private static int nCk(int n, int k) {
		if (n < k) {
			return -1;
		}

		int start = n;
		int stop = Math.max(k, n - k);
		int start2 = Math.min(k, n - k);
		int stop2 = 1;

		int val = 1;
		for (int i = start; i > stop; i--) {
			val *= i;
		}
		for (int i = start2; i > stop2; i--) {
			val /= i;
		}

		return val;
	}

	private static void incrementTuple(int[] tuple, int n, int index) {
		if (index == -1) {
			return;
		}
		if (tuple[index] == n - (tuple.length - index)) {
			incrementTuple(tuple, n, index - 1);
		} else {
			int start = tuple[index];
			for (int i = index; i < tuple.length; i++) {
				tuple[i] = ++start;
			}
		}
	}

}
