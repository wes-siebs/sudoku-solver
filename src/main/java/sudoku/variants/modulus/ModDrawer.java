package main.java.sudoku.variants.modulus;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import main.java.sudoku.components.Board;
import main.java.sudoku.components.Cell;
import main.java.sudoku.components.Settings;
import main.java.sudoku.variants.VariantDrawer;

public class ModDrawer extends VariantDrawer {

	@Override
	public void drawVariant(Board board) {
		drawCircles((ModBoard) board);
	}

	@Override
	public boolean overFrame() {
		return true;
	}

	public void drawCircles(ModBoard board) {
		Settings.setFontSize(12);
		List<ModCircle> circles = board.modCircles;

		for (ModCircle circle : circles) {
			draw(circle);
		}
	}

	public static void draw(ModCircle circle) {
		int[] pos = Settings.boxPos;
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

		Graphics2D g = Settings.g;
		g.setColor(Color.WHITE);
		g.fillOval(x, y, pos[1], pos[1]);
		g.setColor(Color.BLACK);
		g.drawOval(x, y, pos[1], pos[1]);
		g.drawString("" + circle.mod, x + 7, y + 15);
	}

}
