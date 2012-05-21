package adapters.activators;


import actions.*;
import adapters.CardNameAdapter;
import roma.*;
import cards.Card;
import cards.activators.ConsiliariusParams;
import cards.activators.MachinaParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Machina.
 * @author Supalosa
 *
 */
public class MachinaAdapterActivator extends GenericAdapterActivator implements MachinaActivator {

	MachinaParams params;
	
	Card[] fieldList;
	
	public MachinaAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);;
		
		this.params = (MachinaParams) theCard.getParams();
		
		// Need a copy of fieldList
		// so we can destroy the values (i.e. avoid doubling
		// up of cards with same name)
		fieldList = game.getField().getSide(game.whoseTurn());
		
	}
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}


	/**
	 * Get the card on the field named 'card', and declare its new position
	 */
	@Override
	public void placeCard(framework.cards.Card card, int diceDisc) {
		boolean foundCard = false;
		
		for (int pos = 0; pos < Game.FIELD_SIZE && foundCard == false; pos++) {
			// Get the card in that position
			Card romaCard = fieldList[pos];
			if (romaCard != null) {
				// Get its acceptance name name
				framework.cards.Card acceptanceName = CardNameAdapter.getAcceptanceCard(romaCard.getName());
				
				// Found the match!
				if (acceptanceName == card) {
					params.addPosition(pos, diceDisc-1); 
					// Remove the entry in the field copy (so it won't get picked up again)
					fieldList[pos] = null;
					foundCard = true;
					
					//System.out.println ("Consil: " + card + " [" + pos + "] -> " + diceDisc);
				}
				
			}
		}
		
	}


}
