package actions;

import java.util.*;
import roma.*;
import cards.*;

public class ViewCardAction implements PlayerAction {

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
		Card [][] field = game.getField();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] != null) {
					cardOptions.add(field[i][j]);
				}
			}
		}
		
		// add all the cards in my hand
		for (Card c : game.getCurrentPlayer().getHand()) {
			cardOptions.add(c);
		}
		
		selectedCard = game.getController().getCard(cardOptions, "Please select a card to inspect:");
		
		
	}

}
