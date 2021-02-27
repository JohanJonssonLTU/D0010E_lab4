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
 * 
 * @author jj
 */

public class GamePanel extends JPanel implements Observer{

	/**
	 * The dimensions of a game grid square.
	 * 
	 * @author jj
	 */
	public final int UNIT_SIZE = 20;
	private GameGrid grid;
	Dimension d;
	
	/**
	 * The constructor
	 * 
	 * @param grid The grid that is to be displayed
	 */
	public GamePanel(GameGrid grid){
		
		System.out.println("new GamePanel");
		
		this.grid = grid;
		grid.addObserver(this);
		this.d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
		
		System.out.println("GamePanel()." + d);
		System.out.println("GamePanel().setBackground(" + Color.white + ")");
		
	}

	/**
	 * Returns a grid position given pixel coordinates
	 * of the panel, calculating integer values for x and y through division with the grid 
	 * square dimensions 
	 * 
	 * @param x the x coordinates
	 * @param y the y coordinates
	 * @return an integer array containing the [x, y] grid position
	 */
	public int[] getGridPosition(int x, int y){
		int[] gridPosition = {x / UNIT_SIZE, y / UNIT_SIZE};
		return gridPosition;
	}
	
	/**
	 * Repaints the main game window.
	 * 
	 * @author jj
	 */
	public void update(Observable arg0, Object arg1) {
		
		System.out.println("GamePanel.update()");
		
		this.repaint();
	}
	
	/**
	 * Iterates over a 2-dimensional ArrayList and paints adjecent, 
	 * framed squares according to indices. 
	 * 
	 * @author jj
	 */
	public void paintComponent(Graphics g){
		
//		System.out.println("GamePanel.paintComponent()");
//		System.out.println(grid);
		
		super.paintComponent(g);
		
		for (int y = 0; y < grid.getSize(); y++) {
						
			for (int x = 0; x < grid.getSize(); x++) {
				
				if (grid.getLocation(x, y) == 0) {
					g.setColor(Color.WHITE);
					g.fillRect(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.BLACK);
					g.drawRect(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				}
				
				if (grid.getLocation(x, y) == 1) {
					g.setColor(Color.WHITE);
					g.fillRect(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.BLACK);
					g.drawOval(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.fillOval(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.BLACK);
					g.drawRect(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				}
				
				if (grid.getLocation(x, y) == -1) {
					g.setColor(Color.WHITE);
					g.fillRect(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.BLACK);
					g.drawOval(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.WHITE);
					g.fillOval(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.BLACK);
					g.drawRect(UNIT_SIZE*x, UNIT_SIZE*y, UNIT_SIZE, UNIT_SIZE);
				}
					
			}
			
		}
		
	}
	
}
