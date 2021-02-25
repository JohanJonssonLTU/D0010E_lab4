/*
 * Created on 2007 feb 8
 */
package lab4.data;

import java.util.Observable;
import java.util.Observer;

import lab4.client.GomokuClient;

/**
 * Represents the state of a game
 */

public class GomokuGameState extends Observable implements Observer{

   // Game variables
	private final int DEFAULT_SIZE = 15;
	private GameGrid gameGrid;
    //Possible game states
	private final int NOT_STARTED = 0;
	private int currentState;
	private int MY_TURN = 1;
	private int OTHER_TURN = 2;
	private int IS_FINISHED = 3;
	private GomokuClient client;
	
	private String message;
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		client = gc;
		client.addObserver(this);
		gc.setGameState(this);
		currentState = NOT_STARTED;
		gameGrid = new GameGrid(DEFAULT_SIZE);
	}
	

	/**
	 * Returns the message string
	 * 
	 * @return the message string
	 */
	public String getMessageString(){
		return message;
	}
	
	/**
	 * Returns the game grid
	 * 
	 * @return the game grid
	 */
	public GameGrid getGameGrid(){
		return gameGrid;
	}

	/**
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		
		if (this.client.getConnectionStatus() == 0) {
			message = "No connection";
		} else if (this.currentState != MY_TURN) {
			message = "It's not your turn yet!";
		} else if (this.gameGrid.getLocation(x, y) != 0) {
			message = "This spot is not empty!";
		} else {
			this.client.sendNewGameMessage();
			message = "A move has been made.";
			
			if (this.gameGrid.isWinner(1)){
				this.client.sendNewGameMessage();
				message = "We've got ourselves a winner!";
				currentState = IS_FINISHED;
			}
			
		}
		setChanged();
		notifyObservers();
		System.out.println("My turn");
		System.out.println(gameGrid);
	}
	
	/**
	 * Starts a new game with the current client
	 */
	public void newGame(){
		gameGrid.clearGrid();
		currentState = OTHER_TURN;
		message = "New game, the other persons turn";
		// SendNew någonting
		// Observers
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Other player has requested a new game, so the 
	 * game state is changed accordingly
	 */
	public void receivedNewGame(){
		gameGrid.clearGrid();
		currentState = MY_TURN;
		message = "New game, it's your turn";
		notifyObservers();
		setChanged();
		// Samma som newGame men tvärtom, din tur...
	}

	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		this.gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "The other player has disconnected.";
		notifyObservers();
		setChanged();
	}
	
	/**
	 * The player disconnects from the client
	 */
	public void disconnect(){
		this.gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "You have been disconnected.";
		notifyObservers();
		setChanged();
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {

		this.gameGrid.move(x,y,2);

		if (this.gameGrid.isWinner(2)) {
			message = "You Loose!";
			currentState = IS_FINISHED;
		}
		setChanged();
		notifyObservers();
		System.out.println("Others turn");
		System.out.println(gameGrid);
	}
	
	public void update(Observable o, Object arg) {
		
		switch(client.getConnectionStatus()){
		case GomokuClient.CLIENT:
			message = "Game started, it is your turn!";
			currentState = MY_TURN;
			break;
		case GomokuClient.SERVER:
			message = "Game started, waiting for other player...";
			currentState = OTHER_TURN;
			break;
		}
		setChanged();
		notifyObservers();
		
		
	}
	
}
