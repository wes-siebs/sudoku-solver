package main.java.sudoku.components;

import java.awt.Font;
import java.awt.Graphics2D;

public class Settings {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	public static final int MARGIN = 20;
	public static final int OFFSET_X = 8;
	public static final int OFFSET_Y = 31;
	
	public final static boolean show[] = new boolean[10];
	public static Graphics2D g;
	public static int x = MARGIN;
	public static int py = MARGIN;
	public static int gy = WIDTH;
	public static int w = WIDTH - (MARGIN << 1);
	public static int h = HEIGHT - WIDTH - MARGIN;
	public static Cell[][] cells;
	public static int boxPos[];
	
	private static int fontSize = 0;
	
	public static void setFontSize(int fontSize) {
		if (Settings.fontSize != fontSize) {
			Graphics2D g = Settings.g;
			g.setFont(new Font(g.getFont().getName(), Font.PLAIN, fontSize));
			Settings.fontSize = fontSize;
		}
	}

}
