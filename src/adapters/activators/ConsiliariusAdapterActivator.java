package adapters.activators;

import java.util.*;

import actions.*;
import adapters.CardNameAdapter;
import roma.*;
import cards.Card;
import cards.activators.ConsiliariusParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Consiliarius.
 * @author Supalosa
 *
 */
public class ConsiliariusAdapterActivator implements ConsiliariusActivator {

	Card theCard;
	int fieldPosition;
	Game game;

	ConsiliariusParams params;
	
	public ConsiliariusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.params = (ConsiliariusParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		IPlayerAction action = new ActivateCardAction(params);
		MockController controller = (MockController)game.getController();

		controller.insertInput(Integer.toString(fieldPosition));
		

		action.execute(game.getGameVisor());
	}



	/**
	 * Get the card on the field named 'card', and declare its new position
	 */
	@Override
	public void placeCard(framework.cards.Card card, int diceDisc) {
		
		for (int pos = 0; pos < Game.FIELD_SIZE; pos++) {
			// Get the card in that position
			Card romaCard = game.getField().getCard(game.whoseTurn(), pos);
			if (romaCard != null) {
				// Get its acceptance name name
				framework.cards.Card acceptanceName = CardNameAdapter.getAcceptanceCard(romaCard.getName());
				
				/* This will NOT work for doubled up cards!!! */
				if (acceptanceName == card) {
					params.addPosition(pos, diceDisc-1); 
				}
			}
		}
		
	}


}
