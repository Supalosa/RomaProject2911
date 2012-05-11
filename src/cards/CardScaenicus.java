package cards;

import java.util.ArrayList;
import java.util.List;

import roma.*;
import enums.*;

public class CardScaenicus extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Scaenicus;
	}

	@Override
	public int getCostToPlay() {
		return 8;
	}

	@Override
	public int getDiceToActivate() {
		return 1;
	}

	@Override
	public boolean isBuilding() {
		return false;
	}

	@Override
	public String getName() {
		return "Scaenicus";
	}

	@Override
	public String getDescription() {
		return "He performs no action of his own but can copy the action of any " +
				"of the player's own face-up character cards, and the next time round" +
				" that of another.";
	}

	@Override
	public int getDefense() {
		return 3;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		
		List<Card> characters = new ArrayList<Card>();
		for (Card c : g.getField().getSideAsList(g.whoseTurn())) {
			if (!c.isBuilding()) {
				characters.add(c);
			}
		}
		
		Card selectedCard = null;
		selectedCard = g.getController().getCard(characters, "Select a character card to lay.");
		while (selectedCard == null) {
			selectedCard = g.getController().getCard(characters, "Invalid card. Select a character card to lay.");
		}
		
		selectedCard.performEffect(g, pos);
		
		if (selectedCard.getID() != CardNames.Scaenicus) {
			performed = true;
		}
		
		return performed;

	}

}
