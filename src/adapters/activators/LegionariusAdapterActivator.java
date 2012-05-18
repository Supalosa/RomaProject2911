package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Legionarius.
 * @author Supalosa
 *
 */
public class LegionariusAdapterActivator implements LegionariusActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	MockController controller;
	LegionariusParams params;
	
	public LegionariusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.controller = (MockController)game.getController();
		this.params = (LegionariusParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		
		IPlayerAction action = new ActivateCardAction(params);	
		
		// Enter the dice disc you want to activate...
		controller.insertInput(Integer.toString(fieldPosition));
				
				
		action.execute(game.getGameVisor());
		
	}



	@Override
	public void giveAttackDieRoll(int roll) {
		params.setBattleDie(roll);
	}
}
