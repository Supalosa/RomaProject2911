package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Haruspex.
 * @author Supalosa
 *
 */
public class HaruspexAdapterActivator extends GenericAdapterActivator implements HaruspexActivator {

	HaruspexParams params;
	
	public HaruspexAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		this.params = (HaruspexParams) theCard.getParams();
		
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
