package main.java.sudoku.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.java.sudoku.variants.modulus.ModBoard;
import main.java.sudoku.variants.modulus.ModDrawer;

public class Drawer {

	protected final int boxPos[];
	private int fontSize = 0;

	public Drawer() {
		this.boxPos = new int[28];
		for (int i = 0; i < boxPos.length; i++) {
			boxPos[i] = i * Settings.w / 27;
		}
	}

	protected void setFontSize(int fontSize) {
		if (this.fontSize != fontSize) {
			Graphics2D g = Settings.g;
			g.setFont(new Font(g.getFont().getName(), Font.PLAIN, fontSize));
			this.fontSize = fontSize;
		}
	}

	protected void drawFrame() {
		Graphics2D g = Settings.g;
		int x = Settings.x;
		int y = Settings.py;
		int size = Settings.w;

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		g.drawRect(x, y, size, size);
		for (int i = 1; i < 3; i++) {
			g.drawLine(x, y + boxPos[i * 9], x + size, y + boxPos[i * 9]);
			g.drawLine(x + boxPos[i * 9], y, x + boxPos[i * 9], y + size);
		}
		g.setStroke(new BasicStroke(1));
		for (int i = 1; i < 9; i++) {
			g.drawLine(x, y + boxPos[i * 3], x + size, y + boxPos[i * 3]);
			g.drawLine(x + boxPos[i * 3], y, x + boxPos[i * 3], y + size);
		}
	}

	protected void drawNotes() {
		Graphics2D g = Settings.g;
		int x = Settings.x;
		int y = Settings.py;

		g.setColor(Color.LIGHT_GRAY);
		for (int note = 0; note < 9; note++) {
			if (!Settings.show[note]) {
				continue;
			}
			for (int i = 0; i < 9; i++) {
				int yy = y + boxPos[3 * i + note / 3];
				for (int j = 0; j < 9; j++) {
					int xx = x + boxPos[3 * j + note % 3];
					if (Settings.cells[i][j].getNote(note + 1)) {
						g.fillRect(xx, yy, boxPos[1], boxPos[1]);
					}
				}
			}
		}
	}

	protected void drawValues() {
		Graphics2D g = Settings.g;
		int x = Settings.x;
		int y = Settings.py;

		setFontSize(25);
		g.setColor(Color.BLACK);
		for (Cell[] row : Settings.cells) {
			for (Cell cell : row) {
				if (cell.getValue() > 0) {
					g.drawString(cell.toString(), x + 25 + boxPos[cell.column * 3], y + 40 + boxPos[cell.row * 3]);
				}
			}
		}
	}

	public void drawPuzzle(Board board) {
		drawNotes();
		drawValues();
		drawFrame();

		setFontSize(12);
		if (board instanceof ModBoard) {
			ModDrawer.draw(board, Settings.g, boxPos);
		}
	}

	public void drawGraph(int[] vals, double progress, String note) {
		Graphics2D g = Settings.g;
		int x = Settings.x;
		int y = Settings.gy;
		int w = Settings.w;
		int h = Settings.h;

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1));
		int[] adjusted = adjust(vals, h);
		g.setStroke(new BasicStroke(2));
		for (int i = 1; i < vals.length; i++) {
			int x1 = x + (i - 1) * w / (vals.length - 1);
			int x2 = x + i * w / (vals.length - 1);
			int y1 = y + adjusted[i - 1];
			int y2 = y + adjusted[i];
			g.drawLine(x1, y1, x2, y2);
		}

		if (progress < 1) {
			g.setColor(new Color(0, 255, 0, 100));
			g.fillRect((int) (x + (progress * w)), y, w / vals.length, h);
		}

		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		g.drawRect(x, y, w, h);

		g.drawString(note, x, y - 3);
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
