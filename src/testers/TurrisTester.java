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
public class TurrisTester implements ITestSuite {

	@Override
	public void run() {
		MockController controller;
		Game g;
		GameVisor gv;
		PlayerAction action;
		boolean hadModifier;
		
		controller = new MockController();
		g = new Game(controller);
		controller.setGame(g);
		gv = g.getGameVisor();

		
		System.out.println ("> Setting up field...");
		Card testTurris = new CardTurris();
		Card testLegionariusOwn = new CardLegionarius();
		Card testLegionariusEnemy = new CardLegionarius();
		Card testLegionariusOwn2 = new CardLegionarius();
		
		gv.addCard(0, testTurris);
		gv.addCard(0, testLegionariusOwn);
		gv.addCard(0, testLegionariusOwn2);
		gv.addCard(1, testLegionariusEnemy);
		
		action = new LayCardAction(); // place legionarius first
		controller.insertInput ("1"); // pls select card
		controller.insertInput ("6"); // pls select dice disc
		action.execute(gv);
		System.out.println (">> Testing initial Legionarius defense");
		assert (testLegionariusOwn.getRealDefense() == 5);
		
		action = new LayCardAction();
		controller.insertInput ("0"); // pls select card
		controller.insertInput ("3"); // pls select dice disc		
		action.execute(gv);
		
		System.out.println (">> Testing initial Turris defense");
		assert (testTurris.getRealDefense() == 6);
		
		controller.insertInput("Y");
		action = new EndTurnAction();
		action.execute(gv);
		
		action = new LayCardAction();
		controller.insertInput ("0"); // pls select card
		controller.insertInput ("2"); // pls select dice disc		
		action.execute(gv);
		
		System.out.println (">> Testing enemy Legionarius defense");
		assert (testLegionariusEnemy.getRealDefense() == 5);
		
		assert (g.getField().getCard(0, 2) == testTurris);
		assert (g.getField().getCard(0, 5) == testLegionariusOwn);
		assert (g.getField().getCard(1, 1) == testLegionariusEnemy);

		System.out.println ("> Testing aura is on friendly card...");
		
		hadModifier = false;
		for (IModifier mod : testLegionariusOwn.getModifiers()) {
			if (mod.getName().equals(TurrisAura.NAME)) {
				hadModifier = true;
			}
		}
		assert (hadModifier);
		
		System.out.println ("> Testing friendly card received buff to defense");
		assert (testLegionariusOwn.getRealDefense() == 6);
		
		System.out.println ("> Laying another Legionarius, checking buff");
		
		controller.insertInput("Y");
		action = new EndTurnAction();
		action.execute(gv);
		
		action = new LayCardAction();
		controller.insertInput ("0"); // pls select card
		controller.insertInput ("4"); // pls select dice disc		
		action.execute(gv);
		
		assert (testLegionariusOwn2.getRealDefense() == 6);
		
		System.out.println ("> Removing Legionarius, checking buff removed from legionarius");
		gv.getField().setCard(0, 3, null);
		
		assert (testLegionariusOwn2.getRealDefense() == 5);
		
		System.out.println ("> Removing Turris from field.");
		gv.getField().setCard(0, 2, null);
		assert (testLegionariusOwn.getRealDefense() == 5);
		assert (testLegionariusEnemy.getRealDefense() == 5);
		assert (testLegionariusOwn2.getRealDefense() == 5);
		
		System.out.println ("All '" + getTestName() + "' tests passed.");
		
	}

	@Override
	public String getTestName() {
		return "Turris Tester";
	}

}
