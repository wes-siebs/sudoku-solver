package main.java.sudoku;

public class Utilities {
	
	public static int[][] nCkTuples(int n, int k, int index) {
		int size = factorial(n) / factorial(n - k) / factorial(k);
		
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
	
	private static int factorial(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("cannot return factorial of negative number: " + n);
		}
		if (n == 0) {
			return 1;
		} else {
			return n * factorial(n - 1);
		}
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
