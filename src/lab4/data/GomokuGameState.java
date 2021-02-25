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
	private int OTHER_TURN = -1;
	private int IS_FINISHED = 0;
	private GomokuClient client;
	
	private String message = "Welcome To Gomoku!";
	
	/**
	 * The constructor
	 * 
	 * @param gc The client used to communicate with the other player
	 */
	public GomokuGameState(GomokuClient gc){
		
		System.out.println("new GomokuGameState");
		
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
	 * This player makes a move at a specified location
	 * 
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void move(int x, int y){
		
//		gameGrid.move(x, y, 1);
//		currentState = OTHER_TURN;
		
		if (client.getConnectionStatus() == 0) {
			message = "No connection";
			System.out.println("No connection");
		} else if (currentState != MY_TURN) {
			message = "It's not your turn yet!";
			System.out.println("It's not your turn yet!");
		} else if (currentState == 0) {
			message = "No game in progress!";
			System.out.println("No game in progress!");
		} else if (gameGrid.getLocation(x, y) != 0) {
			message = "This spot is not empty!";
			System.out.println("This spot is not empty!");
		} else {
			
			gameGrid.move(x, y, 1);
			currentState = OTHER_TURN;
			client.sendMoveMessage(x, y);
			message = "A move has been made.";
			
			if (gameGrid.isWinner(1)){
				client.sendNewGameMessage();
				message = "We've got ourselves a winner!";
				currentState = IS_FINISHED;
			}
			
		}
		setChanged();
		notifyObservers();
//		System.out.println("My turn");
//		System.out.println(gameGrid);
	}
	
	/**
	 * The player receives a move from the other player
	 * 
	 * @param x The x coordinate of the move
	 * @param y The y coordinate of the move
	 */
	public void receivedMove(int x, int y) {
		
		System.out.println("GomokuGameState().receivedMove()");

		gameGrid.move(x,y,-1);
		currentState = MY_TURN;
		
		if (gameGrid.isWinner(-1)) {
			message = "You Loose!";
			currentState = IS_FINISHED;
		}
		setChanged();
		notifyObservers();
//		System.out.println("Others turn");
//		System.out.println(gameGrid);
	}
	
	/**
	 * The connection to the other player is lost, 
	 * so the game is interrupted
	 */
	public void otherGuyLeft(){
		
		System.out.println("GomokuGameState().otherGuyLeft()");
		
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
		
		System.out.println("GomokuGameState().disconnect()");
		
		this.gameGrid.clearGrid();
		currentState = NOT_STARTED;
		message = "You have been disconnected.";
		notifyObservers();
		setChanged();
	}
	
	public void update(Observable o, Object arg) {
		
		System.out.println("GomokuGameState().update()");
		
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
	
	public String toString() {
		return 
			"currentState = " + currentState + "\n" +
			MY_TURN + " = MY_TURN" + "\n" +
			OTHER_TURN + " = OTHER_TURN" + "\n" +
			IS_FINISHED + " = IS_FINISHED" 
			;
	}
	
}
