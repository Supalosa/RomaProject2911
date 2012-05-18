package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Onager.
 * @author Supalosa
 *
 */
public class OnagerAdapterActivator implements OnagerActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	MockController controller;
	OnagerParams params;
	
	public OnagerAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.controller = (MockController)game.getController();
		this.params = (OnagerParams) theCard.getParams();
		
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



	@Override
	public void chooseDiceDisc(int diceDisc) {
		params.setPositionToAttack(diceDisc-1);
		
	}
}
