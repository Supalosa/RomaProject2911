package actions;

import java.util.*;
import roma.*;
import cards.*;

public class ViewCardAction extends PlayerAction {

	private Card selectedCard = null;
	GameVisor game;
	
	public void execute(GameVisor g) {
		game = g;
		
		query ();
		if (selectedCard != null) {
			game.getController().showCard (selectedCard);
		} else {
			game.getController().showMessage("Invalid card.");
		}
	}

	public String getDescription() {
		return "Inspect Card";
	}
	
	private void query () {
		List<Card> cardOptions = new ArrayList<Card>();
		// add all the cards from field
		Field field = game.getField();
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {
			for (int j = 0; j < Game.FIELD_SIZE; j++) {
				if (field.getCard(i, j) != null) {
					cardOptions.add(field.getCard(i, j));
				}
			}
		}
		
		// add all the cards in my hand
		for (Card c : game.getCurrentPlayer().getHand()) {
			cardOptions.add(c);
		}
		
		selectedCard = game.getController().getCard(cardOptions, "Please select a card to inspect:");
		
		
	}

	// always available
	public boolean isVisible(GameVisor g) {
		return true;
	}

}
