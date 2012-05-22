package adapters.activators;

import java.util.*;
import adapters.*;
import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Senator.
 * 
 * @author Supalosa
 * 
 */
public class ArchitectusAdapterActivator extends GenericAdapterActivator
		implements ArchitectusActivator {

	ArchitectusParams params;
	Vector<Card> handCopy; // copy of the hand for mapping purposes

	@SuppressWarnings("unchecked")
	public ArchitectusAdapterActivator(int fieldPosition, Game game,
			Card theCard) {
		super(fieldPosition, game, theCard);

		params = (ArchitectusParams) theCard.getParams();

		handCopy = (Vector<Card>) game.getCurrentPlayer().getHand().clone();
	}

	@Override
	public void complete() {

		execute(params);

	}

	/**
	 * Sets the first instance of 'card' in the hand to be placed on position
	 * diceDisc.
	 * 
	 * The actual movement of cards occurs only in PerformEffect.
	 * 
	 * There is no 'floating card' state, so if a test is added to test this,
	 * this card will need to be changed.
	 * 
	 */
	@Override
	public void layCard(framework.cards.Card card, int diceDisc) {
		boolean foundCard = false;

		for (int pos = 0; pos < handCopy.size() && foundCard == false; pos++) {
			// Get the card in that position
			Card romaCard = handCopy.get(pos);
			if (romaCard != null) {
				// Get its acceptance name name
				framework.cards.Card acceptanceName = CardNameAdapter
						.getAcceptanceCard(romaCard.getName());

				// Found the match!
				if (acceptanceName == card) {
					params.addPosition(romaCard, diceDisc);
					// Remove the entry in the field copy (so it won't get
					// picked up again)
					handCopy.set(pos, null);
					foundCard = true;
				}

			}
		}

	}

}
