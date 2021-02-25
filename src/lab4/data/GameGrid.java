package lab4.data;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{
	
	private ArrayList<ArrayList<Integer>> my_grid = new ArrayList<>();
	private int size;
	
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = -1;
	private static final int INROW = 5;

	/**
	 * Constructor
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
		return my_grid.get(x).get(y);
	}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	public int getSize(){
		return this.size;
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
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		
		int rowCounter = 0;
		int columnCounter = 0;
		int diagonalCounter = 0;
		int newRow;
		int newColu;

		for (int row = 0; row < size; row++) {
			
			rowCounter = 0;
			columnCounter = 0;
			
			for (int column = 0; column < size; column++) {

				if (my_grid.get(row).get(column) == player) {
					rowCounter++;
				} else {
					rowCounter = 0;
				}
				
				if (my_grid.get(column).get(row) == player) {
					columnCounter++;
				} else {
					columnCounter = 0;
				}
				
				if (rowCounter == INROW){
					return true;
				}
				
			}
			
		}
		
		for (int row = 0; row < size; row++) {
			
			for (int colu = 0; colu < size; colu++) {
				
				diagonalCounter = 0;
				newRow = row;
				newColu = colu;
				
				try {
					
					while (my_grid.get(newRow).get(newColu) == player) {
						diagonalCounter++;
						newRow++;
						newColu++;
						if (diagonalCounter == INROW) {
							return true;
						}
					}
					
				} catch (Exception e) {}
				
				diagonalCounter = 0;
				
				try {
					
					while (my_grid.get(newRow).get(newColu) == player) {
						diagonalCounter++;
						newRow++;
						newColu--;
						if (diagonalCounter == INROW) {
							return true;
						}
					}
					
				} catch (Exception e) {}
				
			}
		}
		return false;
	}
	
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