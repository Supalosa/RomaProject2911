package cards.activators;

import java.util.ArrayList;
import java.util.List;

import roma.*;
import cards.*;

public class AesculapinumParams extends CardParams {
	
	/**
	 * The n'th card on the discard pile
	 */
	int pickedUpCard;
	
	public AesculapinumParams() {
		pickedUpCard = -1;
	}
	
	@Override
	public void query(GameVisor g, int pos) {
		List<Card> characters = new ArrayList<Card>();
		
		for (Card c : g.getDiscardPile().asList()) {
			
			if (!c.isBuilding()) {
				
				characters.add(c);
				
			}
			
		}
		
		if (!characters.isEmpty()) {
			
			Card selected = g.getController().getCard(characters, "Pick the card you wish to add to your hand");
			// Get the position of this card in the discard pile.
			if (selected != null) {
				int discardPosition = 0;
				for (Card c : g.getDiscardPile().asList()) {
					if (c == selected) {
						setPickedUpCard(discardPosition);
					}
					discardPosition ++;
				}
			}
		} else {
			
			setError("There are no Character cards on the discard pile!");
			
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
