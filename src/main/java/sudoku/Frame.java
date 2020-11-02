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

public class Frame implements KeyListener{
	
	private Puzzle puzzle;
	
	private JFrame frame;
	private Image img;
	private Graphics2D g;
	
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
	
	public void main_loop() {
		int[] boxPos = new int[9];
		for (int i = 0; i < boxPos.length; i++) {
			boxPos[i] = i * (SIZE - 40) / 9;
		}
		
		while(true) {
			this.g.setColor(Color.WHITE);
			this.g.fillRect(0, 0, SIZE, SIZE);
			
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
			
			Cell[][] cells = this.puzzle.getBoard().rows;
			for (Cell[] row : cells) {
				for (Cell cell : row) {
					if (cell.value > 0) {
						this.g.drawString(cell.toString(), 40 + boxPos[cell.column], 40 + boxPos[cell.row]);
					}
				}
			}
			
			this.frame.getGraphics().drawImage(this.img, OFFSET_X, OFFSET_Y, null);
			sleep(20);
		}
	}
	
	public void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException exception) {
			System.err.println("Error sleeping");
			exception.printStackTrace(System.err);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.puzzle.solve();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.puzzle.redo();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.puzzle.undo();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
