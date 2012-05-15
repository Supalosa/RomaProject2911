package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
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
	
	public AesculapinumAdapterActivator(int fieldPosition, Game game, Card theCard) {
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
	public void chooseCardFromPile(int indexOfCard) {
		int modifiedPosition = 0; // modified position in a list of character cards only
		int temp = 0;
		int realPos = 0;
		for (Card c : game.getDiscardPile().asList()) {
			if (!c.isBuilding()) {
				if (indexOfCard == realPos) { // match!
					modifiedPosition = temp;
				}
				temp ++;
			}
			realPos ++;
		}
		System.out.println ("ModifiedPosition = " + modifiedPosition);
		
		// Enter the target for sicarius...
		System.out.println ("chooseCardFromPile: " + game.getDiscardPile().asList());
    	
		controller.insertInput (Integer.toString(modifiedPosition));
	}

}
