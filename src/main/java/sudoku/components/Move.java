package main.java.sudoku.components;

import java.util.ArrayList;
import java.util.List;

public class Move {

	public List<Change> changeList;
	public int difficulty;
	public String description = "";

	public Move() {
		this.changeList = new ArrayList<Change>();
		this.difficulty = 0;
	}

	public void addChange(Change change) {
		if (change.isValid()) {
			this.changeList.add(change);
		}
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
