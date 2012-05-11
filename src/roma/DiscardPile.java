package roma;

import cards.Card;

public class DiscardPile extends Pile {

	public void addCard (Card c) {
		c.onEnterDiscard();
		super.addCard(c);
	}
	
	public void removeCard (Card c) {
		c.onLeaveDiscard();
		super.removeCard(c);
	}
}
