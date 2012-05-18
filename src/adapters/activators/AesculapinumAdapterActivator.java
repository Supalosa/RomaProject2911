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
public class AesculapinumAdapterActivator implements AesculapinumActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	MockController controller;
	AesculapinumParams params;
	
	public AesculapinumAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.controller = (MockController)game.getController();
		this.params = (AesculapinumParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		
		IPlayerAction action = new ActivateCardAction(params);	
		
		// Enter the dice disc you want to activate...
		controller.insertInput(Integer.toString(fieldPosition));
				
				
		action.execute(game.getGameVisor());
		
	}
	
	/**
	 * Set the dice disc to be activated.
	 * In our adapter, we have to convert it because the output
	 * skips building cards.
	 */
	@Override
	public void chooseCardFromPile(int indexOfCard) {

		params.setPickedUpCard(indexOfCard);
	}

}
