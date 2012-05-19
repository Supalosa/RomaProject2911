package cards.activators;

import java.util.ArrayList;
import java.util.List;

import roma.*;
import cards.*;

public class HaruspexParams extends CardParams {
	
	/**
	 * The n'th card on the deck
	 */
	int pickedUpCard;
	
	public HaruspexParams() {
		pickedUpCard = -1;
	}
	
	@Override
	public void query(GameVisor g, int pos) {
		List<Card> cards = new ArrayList<Card>();
		
		for (Card c : g.getDeck().asList()) {
			cards.add(c);			
		}
		
		if (cards.isEmpty()) {
			
			Card selected = g.getController().getCard(cards, "Pick the card you wish to add to your hand");
			// Get the position of this card in the discard pile.
			if (selected != null) {
				int deckPosition = 0;
				for (Card c : g.getDeck().asList()) {
					if (c == selected) {
						setPickedUpCard(deckPosition);
					}
					deckPosition ++;
				}
			}
		} else {
			
			setError("There are no cards in the deck!");
			
		}
	}
	
	/**
	 * Sets the card to be picked up.
	 * @param pickedUp
	 */
	public void setPickedUpCard(int pickedUp) {
		pickedUpCard = pickedUp;
	}
	
	/**
	 * Gets the card to be picked up
	 */
	public int getPickedUpCard() {
		return pickedUpCard;
	}
	
	public boolean isValid() {
		return (pickedUpCard != -1);
	}

	
	
}
