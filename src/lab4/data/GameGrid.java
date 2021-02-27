package lab4.data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 * 
 * @author jj and cp
 */

public class GameGrid extends Observable{
	
	private ArrayList<ArrayList<Integer>> my_grid;
	private int size;
	
	/**
	 * Numeric constant representing an empty square.
	 */
	public static final int EMPTY = 0;
	/**
	 * Numeric constant representing a local player.
	 */
	public static final int ME = 1;
	/**
	 * Numeric constant representing a remote player.
	 */
	public static final int OTHER = -1;
	private static final int INROW = 5;

	/**
	 * Constructor.
	 * 
	 * @param size The width/height of the game grid
	 */
	public GameGrid(int size){
		
		System.out.println("new GameGrid");

		this.size = size;

		clearGrid();
		
//		System.out.println("init");
		
	}

	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	public int getLocation(int x, int y){
		return my_grid.get(y).get(x);
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return size;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	public boolean move(int x, int y, int player){
		
		if (my_grid.get(y).get(x) == EMPTY) {
			my_grid.get(y).set(x, player);
			notifyObservers();
			setChanged();
			return true;
		}else {
			return false;
		}

	}
	
	/**
	 * Clears the grid of pieces
	 */
	public void clearGrid(){
		
		my_grid = new ArrayList<>();
		
		for (int row = 0; row < size; row++){
			my_grid.add(row, new ArrayList<Integer>());
//			System.out.println("row");
			for (int column = 0; column < size; column++) {
				my_grid.get(row).add(column, EMPTY);
//				System.out.println("column");
			}
		}
		
		notifyObservers();
		setChanged();
	}
	
	/**
	 * Check if INROW
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		
		int rowCounter = 0;
		int columnCounter = 0;
		int diagonalEastCounter = 0;
		int diagonalWestCounter = 0;
		int newRow = 0;
		int newColumn = 0;

		for (int row = 0; row < size; row++) {
			
			for (int column = 0; column < size; column++) {
				
				if (my_grid.get(row).get(column) == player) {
					rowCounter++;
					if (rowCounter == INROW){
						return true;
					}
				} else {
					rowCounter = 0;
				}
				
				if (my_grid.get(row).get(column) == player) {
					
					newRow = row;
					columnCounter = 0;
					
					try {
						
						while (my_grid.get(newRow).get(column) == player) {
							columnCounter++;
							System.out.println("columnCounter " + columnCounter);
							newRow++;
							if (columnCounter == INROW) {
								return true;
							}
						}
						
					} catch (IndexOutOfBoundsException e) {}
				}
				
				diagonalEastCounter = 0;
				diagonalWestCounter = 0;
				
				try {
					
					newRow = row;
					newColumn = column;
					
					while (my_grid.get(newRow).get(newColumn) == player) {
						diagonalEastCounter++;
						System.out.println("diagonalEastCounter " + diagonalEastCounter);
						newRow++;
						newColumn++;
						if (diagonalEastCounter == INROW) {
							return true;
						}
					}
					
				} catch (IndexOutOfBoundsException e) {}
				
				try {
					
					newRow = row;
					newColumn = column;
					
					while (my_grid.get(newRow).get(newColumn) == player) {
						diagonalWestCounter++;
						System.out.println("diagonalWestCounter" + diagonalWestCounter);
						newRow--;
						newColumn++;
						if (diagonalWestCounter == INROW) {
							return true;
						}
					}
					
				} catch (IndexOutOfBoundsException e) {}
				
			}
			
		}
		return false;
	}
	
	/**
	 * Returns a string drawing a grid in console of 2-dimensional ArrayList contents.
	 * 
	 * @author jj
	 */
	public String toString() {
		
		String theMatrix = "";
		
		for (int i = 0; i < my_grid.size(); i++) {
			for (int j = 0; j < my_grid.get(i).size(); j++) {
				theMatrix += Integer.toString(my_grid.get(i).get(j)) + " ";
			}
			theMatrix += "\n";
		}
		
		theMatrix += "Size = " + getSize();
		
		return theMatrix;
		
	}

}