package adapters.activators;

import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Aesculapinum.
 * @author Supalosa
 *
 */
public class AesculapinumAdapterActivator extends GenericAdapterActivator implements AesculapinumActivator {

	AesculapinumParams params;
	
	public AesculapinumAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		super(fieldPosition, game, theCard);
		
		this.params = (AesculapinumParams) theCard.getParams();
		
	}
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	
	/**
	 * Set the dice disc to be activated.
	 * The index is used to determine the card name; and the first occurance of that card name is removed from
	 * the deck. This behaviour is consistent with the acceptance tests but inconsistent with the game.
	 */
	@Override
	public void chooseCardFromPile(int indexOfCard) {

		params.setPickedUpCard(indexOfCard);
		// peek ahead to see what that card would have been
		Card actualCard = getGame().getDiscardPile().getIndex(indexOfCard);
		params.setPickedUpCardName(actualCard.getID());
		
	}

}
