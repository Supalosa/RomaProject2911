package testers;

import cards.*;
import roma.*;
import actions.*;

/**
 * Tests LayCard and PlayCard
 * @author Supalosa
 *
 */
public class PlayCardActionTester implements ITestSuite {

	@Override
	public void run() {
		MockController controller = new MockController();
		Game g = new Game(controller);
		controller.setGame(g);
		GameVisor gv = g.getGameVisor();
		
		System.out.println ("> Testing hand size initially zero");
		assert (g.getPlayer(0).getHandSize() == 0);
		
		System.out.println ("> Adding cards to player 0's hand. Testing hand size");
		Card testLegionarius = new CardLegionarius();
		Card testScaenicus = new CardScaenicus();
		gv.addCard(0, testLegionarius);
		gv.addCard(0, testScaenicus);
		
		assert (g.getPlayer(0).getHandSize() == 2);
		
		System.out.println ("> Laying Legionarius card onto dice 6");
		IPlayerAction l = new LayCardAction();
		controller.insertInput ("0"); // pls select card
		controller.insertInput ("6"); // pls select dice disc
		
		l.execute(gv);
		assert (g.getField().getCard(0, 5) == testLegionarius);
		
		System.out.println ("> Giving 100 sestertii to Player 1");
		g.getPlayer(0).setMoney(100);
		assert (g.getPlayer(0).getMoney() == 100);
		
		System.out.println ("> Playing Scaenicus card onto dice 3");
		IPlayerAction p = new PlayCardAction();
		controller.insertInput ("0"); // pls select card
		controller.insertInput ("3"); // pls select dice disc
		
		p.execute(gv);
		assert (g.getField().getCard(0, 2) == testScaenicus);
		
		
		System.out.println ("All '" + getTestName() + "' tests passed.");
		
	}

	@Override
	public String getTestName() {
		return "PlayCardAction Tester";
	}

}
