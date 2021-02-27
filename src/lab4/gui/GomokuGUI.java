package lab4.gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;

/**
 * The GUI class
 * 
 * 
 * @author jj
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
	 * Containing all data for the layout of the SpringLayout organizing the main window of Gomoku.
	 * Adds listeners to buttons and mouse input.
	 * 
	 * @param g   The game state that the GUI will visualize
	 * @param c   The client that is responsible for the communication
	 */
	public GomokuGUI(GomokuGameState g, GomokuClient c){
		
		System.out.println("new GomokoGUI");
		
		this.client = c;
		this.gamestate = g;

		this.messageLabel = new JLabel(gamestate.getMessageString());
		
		GamePanel gamePanel = new GamePanel(gamestate.getGameGrid());
		
		JFrame frame = new JFrame("Gumoku");
		Dimension d = new Dimension(gamePanel.UNIT_SIZE * gamestate.getGameGrid().getSize() + 35, 
			gamePanel.UNIT_SIZE * gamestate.getGameGrid().getSize() + 120);
		frame.setMinimumSize(d);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(700,200);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		
		panel.add(gamePanel);
		panel.add(connectButton);
		panel.add(newGameButton);
		panel.add(disconnectButton);
		panel.add(messageLabel);
		frame.setContentPane(panel);
		
//		frame.pack();		
		
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);

		layout.putConstraint(SpringLayout.NORTH, gamePanel, 10, SpringLayout.NORTH, panel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, gamePanel, 0, 
			SpringLayout.HORIZONTAL_CENTER, panel);
		
		layout.putConstraint(SpringLayout.NORTH, connectButton, 10, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, connectButton, -107, 
			SpringLayout.HORIZONTAL_CENTER, gamePanel);
		
		layout.putConstraint(SpringLayout.NORTH, newGameButton, 10, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.WEST, newGameButton, 10, SpringLayout.EAST, connectButton);
		
		layout.putConstraint(SpringLayout.NORTH, disconnectButton, 10, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.WEST, disconnectButton, 10, SpringLayout.EAST, newGameButton);
		
		layout.putConstraint(SpringLayout.NORTH, messageLabel, 10, SpringLayout.SOUTH, connectButton);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, messageLabel, 0, 
			SpringLayout.HORIZONTAL_CENTER, newGameButton);
		
		connectButton.addActionListener(new ActionListener() {
			/**
			 * Initiates a connection window when button "Connect" is depressed.
			 * 
			 * @author jj
			 */
			public void actionPerformed(ActionEvent arg0) {
				ConnectionWindow connectionWindow = new ConnectionWindow(client);
			}
		});
		
		newGameButton.addActionListener(new ActionListener() {
			/**
			 * Starts a new game when button "New Game" is depressed.
			 * 
			 * @author jj
			 */
			public void actionPerformed(ActionEvent arg0) {
				gamestate.newGame();
			}
		});
		
		disconnectButton.addActionListener(new ActionListener() {
			/**
			 * 
			 * 
			 * @author jj
			 */
			public void actionPerformed(ActionEvent arg0) {
				gamestate.disconnect();
			}
		});
		
		gamePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int[] coordinates = gamePanel.getGridPosition(e.getX(), e.getY());
//				gamestate.newGame();
				gamestate.move(coordinates[0], coordinates[1]);
			}
		});
		
		client.addObserver(this);
		gamestate.addObserver(this);
		
//		frame.pack();
				
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
