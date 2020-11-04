package main.java.sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Puzzle;

public class Frame implements KeyListener {

	private Puzzle puzzle;

	private JFrame frame;
	private Image img;
	private Graphics2D g;
	private int selection = 0;

	private static final int SIZE = 400;
	private static final int OFFSET_X = 8;
	private static final int OFFSET_Y = 31;

	public Frame(Puzzle puzzle) {
		this.puzzle = puzzle;

		this.frame = new JFrame("Sudoku Solver");

		this.frame.setSize(SIZE + OFFSET_X * 2, SIZE + OFFSET_Y + OFFSET_X);
		this.frame.getContentPane().setSize(SIZE, SIZE);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);

		this.img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
		this.g = (Graphics2D) this.img.getGraphics();

		this.frame.addKeyListener(this);

		this.frame.setVisible(true);
	}

	public void draw() {
		int[] boxPos = new int[9];
		for (int i = 0; i < boxPos.length; i++) {
			boxPos[i] = i * (SIZE - 40) / 9;
		}

		this.g.setColor(Color.WHITE);
		this.g.fillRect(0, 0, SIZE, SIZE);

		Cell[][] cells = this.puzzle.getBoard().rows;
		this.g.setColor(Color.LIGHT_GRAY);
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				for (int ii = 0; ii < 9; ii++) {
					if (selection > 0 && ii != selection - 1) {
						continue;
					}
					if (cells[i][j].possibilities[ii + 1]) {
						this.g.fillRect(20 + boxPos[j] + (ii % 3) * (SIZE - 40) / 27,
								20 + boxPos[i] + (ii / 3) * (SIZE - 40) / 27, (SIZE - 40) / 27, (SIZE - 40) / 27);
					}
				}
			}
		}

		this.g.setColor(Color.BLACK);
		this.g.setStroke(new BasicStroke(2));
		this.g.drawRect(20, 20, SIZE - 40, SIZE - 40);
		for (int i = 1; i < 3; i++) {
			this.g.drawLine(20, 20 + boxPos[i * 3], SIZE - 20, 20 + boxPos[i * 3]);
			this.g.drawLine(20 + boxPos[i * 3], 20, 20 + boxPos[i * 3], SIZE - 20);
		}
		this.g.setStroke(new BasicStroke(1));
		for (int i = 1; i < 9; i++) {
			this.g.drawLine(20, 20 + boxPos[i], SIZE - 20, 20 + boxPos[i]);
			this.g.drawLine(20 + boxPos[i], 20, 20 + boxPos[i], SIZE - 20);
		}

		for (Cell[] row : cells) {
			for (Cell cell : row) {
				if (cell.value > 0) {
					this.g.drawString(cell.toString(), 40 + boxPos[cell.column], 40 + boxPos[cell.row]);
				}
			}
		}

		this.frame.getGraphics().drawImage(this.img, OFFSET_X, OFFSET_Y, null);
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
		} else if (code >= KeyEvent.VK_0 && code <= KeyEvent.VK_9) {
			this.selection = code - KeyEvent.VK_0;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
