package main.java.sudoku.components;

import java.util.ArrayList;
import java.util.List;

public class Move {
	
	private List<Change> changeList;
	
	public Move() {
		this.changeList = new ArrayList<Change>();
	}
	
	public void addChange(Change change) {
		this.changeList.add(change);
	}
	
	public boolean isEmpty() {
		return this.changeList.isEmpty();
	}
	
	public void apply() {
		for (Change change : this.changeList) {
			change.apply();
		}
	}
	
	public void unapply() {
		for (Change change : this.changeList) {
			change.unapply();
		}
	}

}
