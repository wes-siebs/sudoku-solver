package main.java.sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Puzzle;

public class Frame implements KeyListener {

	private Puzzle puzzle;

	private JFrame frame;
	private Image img;
	private Graphics2D g;
	private boolean[] show;
	private boolean showAll;

	private static final int WIDTH = 600;
	private static final int HEIGHT = 800;
	private static final int OFFSET_X = 8;
	private static final int OFFSET_Y = 31;

	public Frame(Puzzle puzzle) {
		this.puzzle = puzzle;
		this.show = new boolean[9];
		for (int i = 0; i < this.show.length; i++) {
			this.show[i] = true;
		}
		this.showAll = false;

		this.frame = new JFrame("Sudoku Solver");

		this.frame.setSize(WIDTH + OFFSET_X * 2, HEIGHT + OFFSET_Y + OFFSET_X);
		this.frame.getContentPane().setSize(WIDTH, HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);

		this.img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.g = (Graphics2D) this.img.getGraphics();

		this.frame.addKeyListener(this);

		this.frame.setVisible(true);
	}

	public void draw(Board board) {
		this.g.setColor(Color.WHITE);
		this.g.fillRect(0, 0, WIDTH, HEIGHT);

		this.drawPuzzle(20, 20, WIDTH - 40, board);
		this.drawGraph(20, WIDTH, WIDTH - 40, HEIGHT - WIDTH - 20, this.puzzle.getChart());

		this.frame.getGraphics().drawImage(this.img, OFFSET_X, OFFSET_Y, null);
	}

	private void drawPuzzle(int x, int y, int size, Board board) {
		int[] boxPos = new int[27];
		for (int i = 0; i < boxPos.length; i++) {
			boxPos[i] = i * size / 27;
		}

		Cell[][] cells = board.rows;
		this.g.setColor(Color.LIGHT_GRAY);
		for (int note = 0; note < 9; note++) {
			if (!this.show[note]) {
				continue;
			}
			for (int i = 0; i < 9; i++) {
				int yy = y + boxPos[3 * i + note / 3];
				for (int j = 0; j < 9; j++) {
					int xx = x + boxPos[3 * j + note % 3];
					if (cells[i][j].notes[note + 1]) {
						this.g.fillRect(xx, yy, boxPos[1], boxPos[1]);
					}
				}
			}
		}

		this.g.setColor(Color.BLACK);
		this.g.setStroke(new BasicStroke(3));
		this.g.drawRect(x, y, size, size);
		for (int i = 1; i < 3; i++) {
			this.g.drawLine(x, y + boxPos[i * 9], x + size, y + boxPos[i * 9]);
			this.g.drawLine(x + boxPos[i * 9], y, x + boxPos[i * 9], y + size);
		}
		this.g.setStroke(new BasicStroke(1));
		for (int i = 1; i < 9; i++) {
			this.g.drawLine(x, y + boxPos[i * 3], x + size, y + boxPos[i * 3]);
			this.g.drawLine(x + boxPos[i * 3], y, x + boxPos[i * 3], y + size);
		}

		for (Cell[] row : cells) {
			for (Cell cell : row) {
				if (cell.value > 0) {
					this.g.drawString(cell.toString(), x + 20 + boxPos[cell.column * 3], y + 20 + boxPos[cell.row * 3]);
				}
			}
		}
	}

	private void drawGraph(int x, int y, int width, int height, int[] vals) {
		this.g.setColor(Color.BLACK);
		this.g.setStroke(new BasicStroke(1));
		int[] adjusted = adjust(vals, height);
		this.g.setStroke(new BasicStroke(2));
		for (int i = 1; i < vals.length; i++) {
			int x1 = x + (i - 1) * width / (vals.length - 1);
			int x2 = x + i * width / (vals.length - 1);
			int y1 = y + adjusted[i - 1];
			int y2 = y + adjusted[i];
			this.g.drawLine(x1, y1, x2, y2);
		}

		double progress = this.puzzle.getProgress();
		if (progress < 1) {
			this.g.setColor(new Color(0, 255, 0, 100));
			this.g.fillRect((int) (x + (progress * width)), y, width / vals.length, height);
		}

		this.g.setColor(Color.BLACK);
		this.g.setStroke(new BasicStroke(3));
		this.g.drawRect(x, y, width, height);
	}

	private int[] adjust(int[] vals, int height) {
		int max = 10;
		for (int i = 0; i < vals.length; i++) {
			if (vals[i] > max) {
				max = vals[i];
			}
		}

		int[] adjusted = new int[vals.length];
		for (int i = 0; i < vals.length; i++) {
			adjusted[i] = (int) (0.1 * height + 0.8 * height * (max - vals[i]) / max);
		}
		return adjusted;
	}

	@Override
	public void keyTyped(KeyEvent e) {
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
			for (int i = 0; i < this.show.length; i++) {
				this.show[i] = this.showAll;
			}
			this.showAll = !this.showAll;
		} else if (code > KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
			int index = code - KeyEvent.VK_1;
			this.show[index] = !this.show[index];
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
