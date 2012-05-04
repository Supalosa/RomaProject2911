package actions;

import java.util.*;
import roma.*;
import cards.*;

public class ViewCardAction implements PlayerAction {

	private Card selectedCard = null;
	
	public void execute(Game g) {
		query (g);
		if (selectedCard != null) {
			g.getController().showCard (selectedCard);
		} else {
			g.getController().showMessage("Invalid card.");
		}
	}

	public String getDescription() {
		return "Inspect Card";
	}
	
	private void query (Game g) {
		List<Card> cardOptions = new ArrayList<Card>();
		// add all the cards from field
		Card [][] field = g.getField();
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if (field[i][j] != null) {
					cardOptions.add(field[i][j]);
				}
			}
		}
		
		// add all the cards in my hand
		for (Card c : g.getCurrentPlayer().getHand()) {
			cardOptions.add(c);
		}
		
		selectedCard = g.getController().getCard(cardOptions, "Please select a card to inspect:");
		
		
	}

}
