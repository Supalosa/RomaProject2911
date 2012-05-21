package actions;

import java.util.*;
import roma.*;
import cards.*;

// not so important wrt parameters, because no acceptance testing
// and this action is never replayed

public class ViewCardAction implements IPlayerAction {

	private Card selectedCard = null;
	GameVisor game;
	
	@Override
	public String describeParameters() {
		return "N/A";
	}
	
	
	public void execute(GameVisor g) throws AssertionError {
		game = g;
		
		if (selectedCard != null) {
			game.getController().showCard (selectedCard);
		} else {
			game.getController().showMessage("Invalid card.");
		}
		
		// don't need to log this
	}

	public String getDescription() {
		return "Inspect Card";
	}
	
	public void query (GameVisor g) {
		List<Card> cardOptions = new ArrayList<Card>();
		// add all the cards from field
		Field field = g.getField();
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {
			for (int j = 0; j < Game.FIELD_SIZE; j++) {
				if (field.getCard(i, j) != null) {
					cardOptions.add(field.getCard(i, j));
				}
			}
		}
		
		// add all the cards in my hand
		for (Card c : g.getCurrentPlayer().getHand()) {
			cardOptions.add(c);
		}
		
		selectedCard = g.getController().getCard(cardOptions, "Please select a card to inspect:");
		
		
	}

	// always available
	public boolean isVisible(GameVisor g) {
		return true;
	}

}
