package lab4;

import lab4.client.GomokuClient;
import lab4.data.GomokuGameState;
import lab4.gui.GomokuGUI;

public class GomokuMain {

	public static void main(String[] args) {
		
		int portNumber = 4000;
		
//		if (args[0] == null) {
//			portNumber = 4000;
//		} else {
//			portNumber = Integer.parseInt(args[0]);
//		}
		
		GomokuClient gomokuClient = new GomokuClient(portNumber);
		GomokuGameState gomokuGameState = new GomokuGameState(gomokuClient);
		
//		System.out.println("init");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
		
		GomokuGUI gomokuGUI = new GomokuGUI(gomokuGameState, gomokuClient);
		
//		//Move 1
//		gomokuGameState.move(2, 2);
//		System.out.println("My turn 1");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		gomokuGameState.receivedMove(3,2);
//		System.out.println("Others turn 1");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		//Move 2
//		gomokuGameState.move(3, 3);
//		System.out.println("My turn 2");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		gomokuGameState.receivedMove(4,3);
//		System.out.println("Others turn 2");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		//Move 3
//		gomokuGameState.move(4, 4);
//		System.out.println("My turn 3");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		gomokuGameState.receivedMove(5,4);
//		System.out.println("Others turn 3");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		//Move 4
//		gomokuGameState.move(5, 5);
//		System.out.println("My turn 4");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		gomokuGameState.receivedMove(6,5);
//		System.out.println("Others turn 4");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		//Move 5
//		gomokuGameState.move(6, 6);
//		System.out.println("My turn 5");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
//		gomokuGameState.receivedMove(7,6);
//		System.out.println("Others turn 5");
//		System.out.println(gomokuGameState);
//		System.out.println(gomokuGameState.getGameGrid());
//		
	}

}
