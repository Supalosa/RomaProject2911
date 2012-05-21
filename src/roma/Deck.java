package roma;

import cards.Card;

public class Deck extends Pile {

	public void addCard (Card c) {
		c.onEnterDeck();
		super.addCard(c);
	}
	
	public void removeCard (Card c) {
		c.onLeaveDeck();
		super.removeCard(c);
	}
	
}
