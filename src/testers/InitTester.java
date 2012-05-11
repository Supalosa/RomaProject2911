package testers;

import roma.*;
import actions.*;
public class InitTester implements ITestSuite {

	@Override
	public void run() {
		IController controller = new ControllerConsole();
		Game g = new Game(controller);
		controller.setGame(g);
		
		System.out.println ("> Testing turn = 0");
		assert (g.getTurnNumber() == 0);
		
		System.out.println ("> Testing VP pool = 16");
		assert (g.getVictoryStockpile() == 16);
		
		System.out.println ("> Testing Player 1 VP = 10");
		assert (g.getPlayer(0).getVP() == 10);
		
		System.out.println ("> Testing Player 2 VP = 10");
		assert (g.getPlayer(1).getVP() == 10);
		
		System.out.println ("> Testing Player 1 Sestertii = 0");
		assert (g.getPlayer(0).getMoney() == 0);
		
		System.out.println ("> Testing Player 2 Sestertii = 0");
		assert (g.getPlayer(1).getMoney() == 0);
		
		System.out.println ("> Testing field is empty...");
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {
			for (int j = 0; j < Game.FIELD_SIZE; j++) {
				assert (g.getField().getCard(i, j) == null);
			}
		}
				
		System.out.println ("All '" + getTestName() + "' tests passed.");
		
	}

	@Override
	public String getTestName() {
		return "Initial State Tester";
	}

}
