package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Aesculapinum.
 * @author Supalosa
 *
 */
public class EssedumAdapterActivator implements EssedumActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	MockController controller;
	EssedumParams params;
	
	public EssedumAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.controller = (MockController)game.getController();
		this.params = (EssedumParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		
		IPlayerAction action = new ActivateCardAction(params);	
		
		// Enter the dice disc you want to activate...
		controller.insertInput(Integer.toString(fieldPosition));
				
				
		action.execute(game.getGameVisor());
		
	}

}
