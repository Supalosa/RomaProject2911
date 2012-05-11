package cards;

import java.util.ArrayList;
import java.util.List;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardConsiliarius extends Card {

	public CardNames getID() {
		return CardNames.Consiliarius;
	}


	public int getCostToPlay() {
		return 4;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Consiliarius";
	}

	public String getDescription() {
		return "The player picks up their character cards and can then lay them " +
		"again on any dice disc. Buildings can be covered.";
	}

	public int getDefense() {
		return 4;
	}

	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = true;
		
		List<Card> characters = new ArrayList<Card>();
		for (Card c : g.getField().getSideAsList(g.whoseTurn())) {
			if (!c.isBuilding()) {
				characters.add(c);
			}
		}
		
		// remove the cards from the field
		for (Card c : characters) {
			g.getField().removeCard(c);
		}
		
		// replace them
		Card selectedCard = null;
		while (characters.size() > 0) {
			int dicePosition = -1;
			selectedCard = g.getController().getCard(characters, "Select a character card to lay.");
			while (selectedCard == null) {
				selectedCard = g.getController().getCard(characters, "Invalid card. Select a character card to lay.");
			}
			
			g.getController().showField();
			
			while (dicePosition < 1 || dicePosition > Game.FIELD_SIZE) {
				dicePosition = g.getController().getInt("Select a position to lay " + selectedCard.getName() + ":");
			}
			characters.remove(selectedCard);
			g.getField().setCard(g.whoseTurn(), dicePosition-1, selectedCard);

		}
		
		return performed;

	}

}
