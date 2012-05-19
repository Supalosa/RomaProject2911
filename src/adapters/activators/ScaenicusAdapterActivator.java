package adapters.activators;

import actions.*;
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

	ScaenicusParams params;
	
	public ScaenicusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		this.params = (ScaenicusParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		
		execute(params);
		
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
		CardActivator copiedActivator = CardActivatorAdapter.getActivator(cardInPosition.getID(), getFieldPosition(), getGame(), copiedCard);
		
		/*
		 * Store it in Scaenicus's param object
		 */
		params.setCopiedParams(copiedActivator);
		
		//System.out.println ("Created a " + copiedActivator.getClass().getName() + " activator");
		return copiedActivator;
	}
}
