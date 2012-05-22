package adapters.activators;

import adapters.CardActivatorAdapter;
import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Onager.
 * @author Supalosa
 *
 */
public class ScaenicusAdapterActivator extends GenericAdapterActivator implements ScaenicusActivator {

	private ScaenicusParams params;
	
	public ScaenicusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		super(fieldPosition, game, theCard);
		
		this.params = (ScaenicusParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}


	public ScaenicusParams getParams() {
		
		return params;
		
	}
	/**
	 * Looks like we have to return an activator for the card that's in that position
	 */
	@Override
	public CardActivator getScaenicusMimicTarget(int diceDisc) {
		
		Card cardInPosition = getGame().getField().getCard(getGame().whoseTurn(), diceDisc-1);
		
		
		/*
		 * Create a copy of the card
		 */
		Card copiedCard = cardInPosition.getCopy();
		
		/*
		 * Create the appropriate Activator object...
		 */
		GenericAdapterActivator copiedActivator = CardActivatorAdapter.getActivator(cardInPosition.getID(), getFieldPosition(), getGame(), copiedCard);
		
		params.setPositionToCopy(diceDisc-1);
		copiedActivator.setIsCopy(true);
		copiedActivator.setCopier(this);
		/*
		 * Store it in Scaenicus's param object
		 */		
		
		return copiedActivator;
	}
	
}
