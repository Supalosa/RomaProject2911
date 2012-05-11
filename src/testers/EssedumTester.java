package testers;

import modifiers.*;
import cards.*;
import roma.*;
import actions.*;

/**
 * Tests LayCard and PlayCard
 * @author Supalosa
 *
 */
public class EssedumTester implements ITestSuite {

	@Override
	public void run() {
		MockController controller;
		Game g;
		GameVisor gv;
		IPlayerAction action;
		
		controller = new MockController();
		g = new Game(controller);
		controller.setGame(g);
		gv = g.getGameVisor();

		
		System.out.println ("> Setting up field...");
		Card testTurris = new CardTurris();
		Card testLegionariusOwn = new CardLegionarius();
		Card testEssedumEnemy = new CardEssedum();
		Card testLegionariusOwn2 = new CardLegionarius();
		
		gv.addCard(0, testTurris);
		gv.addCard(0, testLegionariusOwn);
		gv.addCard(0, testLegionariusOwn2);
		gv.addCard(1, testEssedumEnemy);
		
		action = new LayCardAction(); // place legionarius first (6)
		controller.insertInput ("1"); // pls select card
		controller.insertInput ("6"); // pls select dice disc
		action.execute(gv);
		System.out.println (">> Testing initial Legionarius defense");
		assert (testLegionariusOwn.getRealDefense() == 5);
		
		action = new LayCardAction(); // place legionarius first (5)
		controller.insertInput ("1"); // pls select card
		controller.insertInput ("5"); // pls select dice disc
		action.execute(gv);
		System.out.println (">> Testing initial Legionarius2 defense");
		assert (testLegionariusOwn2.getRealDefense() == 5);
		
		
		controller.insertInput("Y");
		action = new EndTurnAction();
		action.execute(gv);
		
		action = new LayCardAction(); // place essedum (2)
		controller.insertInput ("0"); // pls select card
		controller.insertInput ("2"); // pls select dice disc		
		action.execute(gv);
		
		System.out.println (">> Testing enemy Essedum defense");
		assert (testEssedumEnemy.getRealDefense() == 3);
		
		assert (g.getField().getCard(0, 4) == testLegionariusOwn2);
		assert (g.getField().getCard(0, 5) == testLegionariusOwn);
		assert (g.getField().getCard(1, 1) == testEssedumEnemy);

	
		
		
		System.out.println ("> Activating Essedum effect");
		int[] diceRolls = {2, 2, 2};
		g.setDiceRolls(diceRolls);
		gv.getCurrentPlayer().setMoney(1000);
		action = new ActivateCardAction();
		controller.insertInput("2"); // essedum on dice 2
		action.execute(gv);
		
		System.out.println ("> Testing defense values");
		assert (testLegionariusOwn2.getRealDefense() == 3);
		assert (testLegionariusOwn.getRealDefense() == 3);
		assert (testEssedumEnemy.getRealDefense() == 3);
		
		System.out.println ("> Activating Essedum effect 2x");
		
		
		action = new ActivateCardAction();
		controller.insertInput("2"); // essedum on dice 2
		action.execute(gv);
		
		System.out.println ("> Testing defense values");
		assert (testLegionariusOwn2.getRealDefense() == 1);
		assert (testLegionariusOwn.getRealDefense() == 1);
		assert (testEssedumEnemy.getRealDefense() == 3);
		
		
		System.out.println ("> Ending Turn, testing restored values");
		controller.insertInput("Y");
		action = new EndTurnAction();
		action.execute(gv);
		
		assert (testLegionariusOwn2.getRealDefense() == 5);
		assert (testLegionariusOwn.getRealDefense() == 5);
		assert (testEssedumEnemy.getRealDefense() == 3);
		
		System.out.println ("> Laying turris");
		action = new LayCardAction(); // place turris (0)
		controller.insertInput ("0"); // pls select card
		controller.insertInput ("2"); // pls select dice disc		
		action.execute(gv);
		
		assert (testLegionariusOwn2.getRealDefense() == 7);
		assert (testLegionariusOwn.getRealDefense() == 7);
		assert (testTurris.getRealDefense() == 6);
		assert (testEssedumEnemy.getRealDefense() == 3);
		
		System.out.println ("> Ending Turn, testing static values");
		controller.insertInput("Y");
		action = new EndTurnAction();
		action.execute(gv);
		
		assert (testLegionariusOwn2.getRealDefense() == 7);
		assert (testLegionariusOwn.getRealDefense() == 7);
		assert (testTurris.getRealDefense() == 6);
		assert (testEssedumEnemy.getRealDefense() == 3);
		
		System.out.println ("> Activating Essedum effect 2x");
		int[] diceRolls2 = {2, 2, 2};
		g.setDiceRolls(diceRolls2);
		
		action = new ActivateCardAction();
		controller.insertInput("2"); // essedum on dice 2
		action.execute(gv);
		
		action = new ActivateCardAction();
		controller.insertInput("2"); // essedum on dice 2
		action.execute(gv);
		
		// check aura lists - should have 3 (2x essedum, 1x turris)
		System.out.println ("> Checking aura list size");
		assert (testLegionariusOwn2.getModifiers().size() == 3);
		assert (testLegionariusOwn.getModifiers().size() == 3);
		assert (testTurris.getModifiers().size() == 2);
		assert (testEssedumEnemy.getModifiers().size() == 0);
		
		System.out.println ("> Checking defense values");
		assert (testLegionariusOwn2.getRealDefense() == 3);
		assert (testLegionariusOwn.getRealDefense() == 3);
		assert (testTurris.getRealDefense() == 2);
		assert (testEssedumEnemy.getRealDefense() == 3);
		
		System.out.println ("> Removing Essedum from field");
		gv.getField().setCard(1, 1, null);
		
		System.out.println ("> Checking defense values");
		assert (testLegionariusOwn2.getRealDefense() == 7);
		assert (testLegionariusOwn.getRealDefense() == 7);
		assert (testTurris.getRealDefense() == 6);
		assert (testEssedumEnemy.getRealDefense() == 3);
		
		System.out.println ("All '" + getTestName() + "' tests passed.");
		
	}

	@Override
	public String getTestName() {
		return "Essedum Tester";
	}

}
