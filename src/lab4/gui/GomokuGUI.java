package lab4.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import lab4.client.GomokuClient;
import lab4.data.GameGrid;
import lab4.data.GomokuGameState;

/*
 * The GUI class
 */

public class GomokuGUI implements Observer{

	private GomokuClient client;
	private GomokuGameState gamestate;
	private JLabel messageLabel;
	private final JButton connectButton = new JButton("Connect");
	private final JButton newGameButton = new JButton("New Game");
	private final JButton disconnectButton = new JButton("Disconnect");
	
	/**
	 * The constructor
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		
		this.client = c;
		this.gamestate = g;

		this.messageLabel = new JLabel();
		
		JPanel panel = new JPanel();
		GameGrid gameGrid = new GameGrid(100);
		GamePanel gameGridPanel = new GamePanel(gameGrid);
		JFrame frame = new JFrame("Gumoku");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.pack();
		frame.setLocation(200,200);
		frame.setVisible(true);	
		
		client.addObserver(this);
		gamestate.addObserver(this);
		
		connectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConnectionWindow connectionWindow = new ConnectionWindow(client);
			}
		});
		
		newGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gamestate.newGame();
			}
		});
		
		disconnectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gamestate.disconnect();
			}
		});
		
	}
	
	class gameGridPanel extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			gamestate.move(50, 50);
		}
	}
	
	public void update(Observable arg0, Object arg1) {
		
		// Update the buttons if the connection status has changed
		if(arg0 == client){
			if(client.getConnectionStatus() == GomokuClient.UNCONNECTED){
				connectButton.setEnabled(true);
				newGameButton.setEnabled(false);
				disconnectButton.setEnabled(false);
			}else{
				connectButton.setEnabled(false);
				newGameButton.setEnabled(true);
				disconnectButton.setEnabled(true);
			}
		}
		
		// Update the status text if the gamestate has changed
		if(arg0 == gamestate){
			messageLabel.setText(gamestate.getMessageString());
		}
		
	}
	
}
