package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Legat.
 * @author Supalosa
 *
 */
public class SicariusAdaptorActivator implements SicariusActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	MockController controller;
	
	public SicariusAdaptorActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.controller = (MockController)game.getController();
		// Enter the dice disc you want to activate...
		controller.insertInput(Integer.toString(fieldPosition));
		
	}
	
	
	
	@Override
	public void complete() {
		
		IPlayerAction action = new ActivateCardAction();	
		action.execute(game.getGameVisor());
		
	}
	
	/**
	 * Set the dice disc to be activated.
	 * In our adapter, we have to convert it because the output
	 * skips building cards.
	 */
	@Override
    public void chooseDiceDisc(int diceDisc) {
		int otherPlayer = (game.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card targetedCard = game.getField().getCard(otherPlayer, diceDisc-1);
		int modifiedPosition = 0; // modified position in a list of character cards only
		int temp = 0;
		for (Card c : game.getField().getSideAsList(otherPlayer)) {
			if (!c.isBuilding()) {
				if (c == targetedCard) { // match!
					modifiedPosition = temp;
				}
				temp ++;
			}
		}
		System.out.println ("ModifiedPosition = " + modifiedPosition);
		
		// Enter the target for sicarius...
    	controller.insertInput (Integer.toString(modifiedPosition));
    }
    

}
