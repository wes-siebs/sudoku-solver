package main.java.sudoku;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.java.sudoku.components.Puzzle;
import main.java.sudoku.components.Settings;

public class Listener implements KeyListener {

	private Puzzle puzzle;
	private Frame frame;

	private boolean shift;
	private boolean control;
	public boolean showAll;

	public Listener(Puzzle puzzle, Frame frame) {
		this.puzzle = puzzle;
		this.frame = frame;
		this.frame.frame.addKeyListener(this);

		for (int i = 0; i < Settings.show.length; i++) {
			Settings.show[i] = true;
		}
		this.showAll = false;
	}

	public void refresh() {
		this.frame.draw(this.puzzle.getBoard(), this.puzzle.getChart(), this.puzzle.getProgress(),
				this.puzzle.getNote());
	}

	@Override
	public void keyTyped(KeyEvent e) {
		this.refresh();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_SPACE) {
			this.puzzle.solve();
		} else if (code == KeyEvent.VK_RIGHT) {
			this.puzzle.redo();
		} else if (code == KeyEvent.VK_LEFT) {
			this.puzzle.undo();
		} else if (code == KeyEvent.VK_0) {
			for (int i = 0; i < Settings.show.length; i++) {
				Settings.show[i] = this.showAll;
			}
			this.showAll = !this.showAll;
		} else if (code > KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
			int index = code - KeyEvent.VK_1;
			Settings.show[index] = !Settings.show[index];
		} else if (code == KeyEvent.VK_R) {
			if (this.shift) {
				if (this.control) {
					while (true) {
						this.puzzle.randomize(0, 30);
						this.puzzle.solve();
						this.refresh();
						if (this.puzzle.isSolved()) {
							break;
						}
					}
				} else {
					while (true) {
						this.puzzle.randomize(9, -1);
						this.puzzle.solve();
						this.refresh();
						if (this.puzzle.isSolved()) {
							break;
						}
					}
				}
			} else {
				this.puzzle.randomize(9, -1);
				System.out.println("Reset");
			}
		} else if (code == KeyEvent.VK_SHIFT) {
			this.shift = true;
		} else if (code == KeyEvent.VK_CONTROL) {
			this.control = true;
		}

		this.refresh();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_SHIFT) {
			this.shift = false;
		} else if (code == KeyEvent.VK_CONTROL) {
			this.control = false;
		}

		this.refresh();
	}
}
