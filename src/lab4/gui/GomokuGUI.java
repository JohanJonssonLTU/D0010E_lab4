package lab4.gui;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

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
		
		System.out.println("new GomokoGUI");
		
		this.client = c;
		this.gamestate = g;

		this.messageLabel = new JLabel(gamestate.getMessageString());
		
		JFrame frame = new JFrame("Gumoku");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(700,200);
		frame.setVisible(true);
		
		JPanel panel = new JPanel();
		GamePanel gamePanel = new GamePanel(gamestate.getGameGrid());
		
		panel.add(gamePanel);
		panel.add(connectButton);
		panel.add(newGameButton);
		panel.add(disconnectButton);
		panel.add(messageLabel);
		frame.setContentPane(panel);
		
		frame.pack();
		
		SpringLayout layout = new SpringLayout();
		panel.setLayout(layout);
		
		layout.putConstraint(SpringLayout.NORTH, gamePanel, 10, SpringLayout.NORTH, panel);
		
		layout.putConstraint(SpringLayout.NORTH, connectButton, 10, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.WEST, connectButton, 5, SpringLayout.WEST, panel);
		
		layout.putConstraint(SpringLayout.NORTH, newGameButton, 10, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.WEST, newGameButton, 10, SpringLayout.EAST, connectButton);
		
		layout.putConstraint(SpringLayout.NORTH, disconnectButton, 10, SpringLayout.SOUTH, gamePanel);
		layout.putConstraint(SpringLayout.WEST, disconnectButton, 10, SpringLayout.EAST, newGameButton);
		
		layout.putConstraint(SpringLayout.NORTH, messageLabel, 10, SpringLayout.SOUTH, connectButton);
		layout.putConstraint(SpringLayout.WEST, messageLabel, 10, SpringLayout.WEST, panel);
		
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
		
		gamePanel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
				int[] coordinates = gamePanel.getGridPosition(e.getY(), e.getX());
				
//				gamestate.newGame();
				gamestate.move(coordinates[0], coordinates[1]);
			}
		});
		
		client.addObserver(this);
		gamestate.addObserver(this);
				
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
