package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.ForumParams;
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
	ForumParams params;
	
	
	public ForumAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.controller = (MockController)game.getController();
		this.params = new ForumParams();
		// Enter the dice disc you want to activate...
		controller.insertInput(Integer.toString(fieldPosition));
		
	}
	
	
	
	@Override
	public void complete() {
		
		IPlayerAction action = new ActivateCardAction(params);	
		action.execute(game.getGameVisor());
		
	}



	@Override
	public void chooseActionDice(int actionDiceValue) {
		params.setForumDie(actionDiceValue);
	}



	@Override
	public void chooseActivateTemplum(boolean activate) {
		params.setUseTemplum(activate);
	}



	@Override
	public void chooseActivateTemplum(int diceValue) {
		//params.setUseTemplum(activate);
		
	}


}
