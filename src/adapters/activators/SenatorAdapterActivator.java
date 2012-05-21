package adapters.activators;

import java.util.Vector;

import actions.*;
import adapters.CardNameAdapter;
import roma.*;
import cards.Card;
import cards.activators.SenatorParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Senator.
 * @author Supalosa
 *
 */
public class SenatorAdapterActivator extends GenericAdapterActivator implements SenatorActivator {

	SenatorParams params;
	Vector<Card> handCopy; // copy of the hand for mapping purposes
	
	public SenatorAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		params = (SenatorParams)theCard.getParams();
		
		handCopy = (Vector<Card>) game.getCurrentPlayer().getHand().clone();
	}
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}


	@Override
	public void layCard(framework.cards.Card card, int diceDisc) {
		boolean foundCard = false;
		
		for (int pos = 0; pos < handCopy.size() && foundCard == false; pos++) {
			// Get the card in that position
			Card romaCard = handCopy.get(pos);
			if (romaCard != null) {
				// Get its acceptance name name
				framework.cards.Card acceptanceName = CardNameAdapter.getAcceptanceCard(romaCard.getName());
				
				// Found the match!
				if (acceptanceName == card) {
					params.addPosition(romaCard, diceDisc); 
					// Remove the entry in the field copy (so it won't get picked up again)
					handCopy.set(pos, null);
					foundCard = true;
					//System.out.println("Found " + card + " in position " + pos + " in the hand, going to pos " + (diceDisc));
				}
				
			}
		}
		
	}

}
