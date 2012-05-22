package cards.activators;

import java.util.ArrayList;
import java.util.List;

import roma.*;
import cards.*;
import enums.CardNames;

public class HaruspexParams extends CardParams {
	
	/**
	 * The n'th card on the deck
	 */
	private int pickedUpCard;
	
	
	/**
	 * We also store the pickedUpCardName so we can check if the wrong card was picked up the next time.
	 */
	private CardNames pickedUpCardName;
	
	
	
	public HaruspexParams() {
		
		pickedUpCard = -1;
	
	}
	
	@Override
	public void query(GameVisor g, int pos) {
		
		List<Card> cardsInDeck = new ArrayList<Card>();
		
		for (Card c : g.getDeck().asList()) {
		
			cardsInDeck.add(c);

		}
		
		if (!cardsInDeck.isEmpty()) {
			
			Card selected = g.getController().getCard(cardsInDeck, "Pick the card you wish to add to your hand");
			// Get the position of this card in the discard pile.
			if (selected != null) {
				
				int deckPosition = 0;
				
				for (Card c : g.getDeck().asList()) {
				
					if (c == selected) {
					
						setPickedUpCard(deckPosition);
						setPickedUpCardName(c.getID());
					
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
	 * 
	 * @param pickedUp
	 */
	public void setPickedUpCard(int pickedUp) {
	
		pickedUpCard = pickedUp;
	
	}

	/**
	 * Sets the CardName that was picked up. This is used to actually get the
	 * card, to ensure the same card comes out every time this Action is
	 * executed (i.e. Telephone Box having the card in a different index
	 */
	public void setPickedUpCardName(CardNames name) {

		pickedUpCardName = name;

	}

	/**
	 * Gets the card to be picked up
	 */
	public int getPickedUpCard() {
		
		return pickedUpCard;
	
	}

	/**
	 * Gets the CardName entry that was picked up. This is the parameter that is
	 * actually used in CardHaruspex to determine which card to pull out
	 * 
	 * @return
	 */
	public CardNames getPickedUpCardName() {
	
		return pickedUpCardName;
	
	}

	public boolean isValid() {
	
		return (pickedUpCard != -1);
	
	}
	
}
