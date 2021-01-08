package main.java.sudoku;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Drawer;
import main.java.sudoku.components.Settings;

public class Frame {

	public JFrame frame;
	private Image img;
	private Drawer drawer;

	public Frame(Drawer drawer) {
		int WIDTH = Settings.WIDTH;
		int HEIGHT = Settings.HEIGHT;
		int OFFSET_X = Settings.OFFSET_X;
		int OFFSET_Y = Settings.OFFSET_Y;

		this.drawer = drawer;

		this.frame = new JFrame("Sudoku Solver");

		this.frame.setSize(WIDTH + OFFSET_X * 2, HEIGHT + OFFSET_Y + OFFSET_X);
		this.frame.getContentPane().setSize(WIDTH, HEIGHT);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setResizable(false);

		this.img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D) this.img.getGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.addRenderingHints(rh);

		this.frame.setVisible(true);

		Settings.g = g;

	}

	public void draw(Board board, int[] chart, double progress, String note) {
		int WIDTH = Settings.WIDTH;
		int HEIGHT = Settings.HEIGHT;
		int OFFSET_X = Settings.OFFSET_X;
		int OFFSET_Y = Settings.OFFSET_Y;

		Settings.g.setColor(Color.WHITE);
		Settings.g.fillRect(0, 0, WIDTH, HEIGHT);

		Settings.cells = board.rows;

		this.drawer.drawPuzzle(board);
		this.drawer.drawGraph(chart, progress, note);

		this.frame.getGraphics().drawImage(this.img, OFFSET_X, OFFSET_Y, null);
	}

}
