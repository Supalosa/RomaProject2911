package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Senator.
 * @author Supalosa
 *
 */
public class SenatorAdapterActivator implements SenatorActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	MockController controller;
	
	public SenatorAdapterActivator(int fieldPosition, Game game, Card theCard) {
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
	public void layCard(framework.cards.Card myCard, int whichDiceDisc) {
		// TODO Auto-generated method stub
		
	}

}
