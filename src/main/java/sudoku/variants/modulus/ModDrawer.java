package main.java.sudoku.variants.modulus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Drawer;
import main.java.sudoku.components.Settings;

public class ModDrawer extends Drawer {

	public static void draw(Board board, Graphics2D g, int[] pos) {
		ModBoard modBoard = (ModBoard) board;
		List<ModCircle> circles = modBoard.modCircles;
		
		for (ModCircle circle : circles) {
			draw(circle, g, pos);
		}
	}
	
	public static void draw(ModCircle circle, Graphics2D g, int[] pos) {
		Cell c1 = circle.c1;
		Cell c2 = circle.c2;
		
		int x = Settings.MARGIN - (pos[1] >> 1);
		int y = Settings.MARGIN - (pos[1] >> 1);
		
		if (c1.row == c2.row) {
			x += pos[3 * c2.column];
			y += (pos[3 * c1.row] + pos[3 * (c1.row + 1)]) / 2;
		} else {
			x += (pos[3 * c1.column] + pos[3 * (c1.column + 1)]) / 2;
			y += pos[3 * c2.row];
		}
		
		g.setColor(Color.WHITE);
		g.fillOval(x, y, pos[1], pos[1]);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, pos[1], pos[1]);
		g.drawString("" + circle.mod, x + 7, y + 15);
	}
}
