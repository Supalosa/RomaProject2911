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
	 * In our adapter, we have to convert it because the output
	 * skips building cards.
	 */
	@Override
	public void chooseCardFromPile(int indexOfCard) {

		params.setPickedUpCard(indexOfCard);
	}

}
