package main.java.sudoku.components;

public abstract class Change {
	
	public abstract void apply();
	
	public abstract void unapply();
	
	public abstract boolean isValid();

}
