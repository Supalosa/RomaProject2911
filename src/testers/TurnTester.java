package testers;

import roma.*;
import actions.*;
public class TurnTester implements ITestSuite {

	@Override
	public void run() {
		MockController controller = new MockController();
		Game g = new Game(controller);
		controller.setGame(g);
		GameVisor gv = g.getGameVisor();
		
		System.out.println ("> Testing turn initially = 0");
		assert (g.getTurnNumber() == 0);
		
		System.out.println ("> Player 1 sends End of Turn. Testing turn = 1");
		controller.insertInput("Y");
		IPlayerAction endTurnAction;
		endTurnAction = new EndTurnAction();
		endTurnAction.execute(gv);
		
		assert (g.getTurnNumber() == 1);
		
		System.out.println ("> Player 2 sends End of Turn. Testing turn = 2");
		controller.insertInput("Y");
		endTurnAction = new EndTurnAction();
		endTurnAction.execute(gv);
		
		assert (g.getTurnNumber() == 2);
		

		
		System.out.println ("All '" + getTestName() + "' tests passed.");
		
	}

	@Override
	public String getTestName() {
		return "Turn cycle tester";
	}

}
