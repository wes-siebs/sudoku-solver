package main.java.sudoku;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.variants.modulus.ModBoard;
import main.java.sudoku.variants.modulus.ModDrawer;

public class Frame {
	
	public JFrame frame;
	private Image img;
	private Graphics2D g;
	boolean[] show;

	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	public static final int MARGIN = 20;
	public static final int OFFSET_X = 8;
	public static final int OFFSET_Y = 31;

	public Frame() {
		this.frame = new JFrame("Sudoku Solver");

		this.frame.setSize(WIDTH + OFFSET_X * 2, HEIGHT + OFFSET_Y + OFFSET_X);
		this.frame.getContentPane().setSize(WIDTH, HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);

		this.img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		this.g = (Graphics2D) this.img.getGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		this.g.addRenderingHints(rh);

		this.frame.setVisible(true);
	}

	public void draw(Board board, int[] chart, double progress, String note) {
		this.g.setColor(Color.WHITE);
		this.g.fillRect(0, 0, WIDTH, HEIGHT);

		int x = MARGIN;
		int py = MARGIN;
		int gy = WIDTH;
		int w = WIDTH - (MARGIN << 1);
		int h = HEIGHT - WIDTH - MARGIN;
		
		this.drawPuzzle(x, py, w, board);
		this.drawGraph(x, gy, w, h, chart, progress, note);

		this.frame.getGraphics().drawImage(this.img, OFFSET_X, OFFSET_Y, null);
	}

	private void drawPuzzle(int x, int y, int size, Board board) {
		int[] boxPos = new int[28];
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
					if (cells[i][j].getNote(note + 1)) {
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

		this.g.setFont(new Font(this.g.getFont().getName(), Font.PLAIN, 25));
		for (Cell[] row : cells) {
			for (Cell cell : row) {
				if (cell.getValue() > 0) {
					this.g.drawString(cell.toString(), x + 25 + boxPos[cell.column * 3], y + 40 + boxPos[cell.row * 3]);
				}
			}
		}
		this.g.setFont(new Font(this.g.getFont().getName(), Font.PLAIN, 12));
		
		if (board instanceof ModBoard) {
			ModDrawer.draw(board, this.g, boxPos);
		}
	}

	private void drawGraph(int x, int y, int width, int height, int[] vals, double progress, String note) {
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

		if (progress < 1) {
			this.g.setColor(new Color(0, 255, 0, 100));
			this.g.fillRect((int) (x + (progress * width)), y, width / vals.length, height);
		}

		this.g.setColor(Color.BLACK);
		this.g.setStroke(new BasicStroke(3));
		this.g.drawRect(x, y, width, height);
		
		this.g.drawString(note, x, y - 3);
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
}
