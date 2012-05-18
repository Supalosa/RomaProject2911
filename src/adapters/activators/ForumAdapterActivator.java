package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Forum.
 * @author Supalosa
 *
 */
public class ForumAdapterActivator implements ForumActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	MockController controller;
	
	public ForumAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.controller = (MockController)game.getController();
		// Enter the dice disc you want to activate...
		controller.insertInput(Integer.toString(fieldPosition));
		
	}
	
	
	
	@Override
	public void complete() {
		
		PlayerAction action = new ActivateCardAction();	
		action.execute(game.getGameVisor());
		
	}



	@Override
	public void chooseActionDice(int actionDiceValue) {
		controller.insertInput(Integer.toString(actionDiceValue));
		
	}



	@Override
	public void chooseActivateTemplum(boolean activate) {
		// TODO Auto-generated method stub
		if (activate) {
			controller.insertInput("Y");
		} else {
			controller.insertInput("N");
		}
	}



	@Override
	public void chooseActivateTemplum(int diceValue) {
		// TODO Auto-generated method stub
		
	}


}
