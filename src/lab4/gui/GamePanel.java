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
		
		System.out.println("new GamePanel");
		
		this.grid = grid;
		grid.addObserver(this);
		Dimension d = new Dimension(grid.getSize()*UNIT_SIZE+1, grid.getSize()*UNIT_SIZE+1);
		this.setMinimumSize(d);
		this.setPreferredSize(d);
		this.setBackground(Color.WHITE);
		
		System.out.println("GamePanel()." + d);
		System.out.println("GamePanel().setBackground(" + Color.white + ")");
		
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
		int[] gridPosition = {x / UNIT_SIZE, y / UNIT_SIZE};
		return gridPosition;
	}
	
	public void update(Observable arg0, Object arg1) {
		
		System.out.println("GamePanel.update()");
		
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		
		System.out.println("GamePanel.paintComponent()");
		System.out.println(grid);
		
		super.paintComponent(g);
		
		for (int row = 0; row < grid.getSize(); row++) {
			
			for (int column = 0; column < grid.getSize(); column++) {
				
				if (grid.getLocation(row, column) == 0) {
					g.setColor(Color.BLACK);
					g.drawRect(UNIT_SIZE*row, UNIT_SIZE*column, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.WHITE);
					g.fillRect(UNIT_SIZE*row+1, UNIT_SIZE*column+1, UNIT_SIZE-2, UNIT_SIZE-2);
				}
				
				if (grid.getLocation(row, column) == 1) {
					g.setColor(Color.BLACK);
					g.drawRect(UNIT_SIZE*row, UNIT_SIZE*column, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.WHITE);
					g.fillRect(UNIT_SIZE*row+1, UNIT_SIZE*column+1, UNIT_SIZE-2, UNIT_SIZE-2);
					g.setColor(Color.BLACK);
					g.drawOval(UNIT_SIZE*row, UNIT_SIZE*column, UNIT_SIZE, UNIT_SIZE);
					g.fillOval(UNIT_SIZE*row, UNIT_SIZE*column, UNIT_SIZE, UNIT_SIZE);
				}
				
				if (grid.getLocation(row, column) == -1) {
					g.setColor(Color.BLACK);
					g.drawRect(UNIT_SIZE*row, UNIT_SIZE*column, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.WHITE);
					g.fillRect(UNIT_SIZE*row+1, UNIT_SIZE*column+1, UNIT_SIZE-2, UNIT_SIZE-2);
					g.setColor(Color.BLACK);
					g.drawOval(UNIT_SIZE*row, UNIT_SIZE*column, UNIT_SIZE, UNIT_SIZE);
					g.setColor(Color.WHITE);
					g.fillOval(UNIT_SIZE*row, UNIT_SIZE*column, UNIT_SIZE, UNIT_SIZE);
				}
					
			}
			
		}
		
	}
	
}
