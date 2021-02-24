package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {
		
		int portNumber = 4000;
		
//		if (args != null) {
//			portNumber = Integer.valueOf(args[0]);
//		}
		
		GomokuClient gomokuClient = new GomokuClient(portNumber);
		GomokuGameState gomokuGameState = new GomokuGameState(gomokuClient);
		GomokuGUI gomokuGUI = new GomokuGUI(gomokuGameState, gomokuClient);
		
	}

}
