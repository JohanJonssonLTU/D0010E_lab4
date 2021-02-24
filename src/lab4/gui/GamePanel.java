package lab4.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import lab4.data.GameGrid;

/**
 * A panel providing a graphical view of the game board
 */

public class GamePanel extends JPanel implements Observer{

	private final int UNIT_SIZE = 20;
	private GameGrid grid;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		return null;
	}
	
	public void update(Observable arg0, Object arg1) {
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		for (int y = 0; y < grid.getSize(); y++) {
			
			if (grid.getLocation(y, 0) == 1) {
				g.setColor(Color.WHITE);
				g.drawRect(UNIT_SIZE*0, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				g.fillRect(UNIT_SIZE*0, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
			}
			
			if (grid.getLocation(y, 0) == 2) {
				g.setColor(Color.BLACK);
				g.drawOval(UNIT_SIZE*0, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				g.fillOval(UNIT_SIZE*0, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
			}
			
			if (grid.getLocation(y, 0) == 3) {
				g.setColor(Color.WHITE);
				g.drawOval(UNIT_SIZE*0, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				g.fillOval(UNIT_SIZE*0, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
			}
			
			for (int x = 1; x < grid.getSize(); x++) {
				
				if (grid.getLocation(y, x) == 1) {
					g.setColor(Color.WHITE);
					g.drawRect(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.fillRect(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				}
				
				if (grid.getLocation(y, x) == 2) {
					g.setColor(Color.BLACK);
					g.drawOval(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.fillOval(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				}
				
				if (grid.getLocation(y, x) == 3) {
					g.setColor(Color.WHITE);
					g.drawOval(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.fillOval(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				}
					
			}
			
		}
		
	}
	
}