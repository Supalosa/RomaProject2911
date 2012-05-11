package cards;

import java.util.ArrayList;
import java.util.List;

import enums.CardNames;

import roma.*;

public class CardMachina extends Card {
	

	public CardNames getID() {
		return CardNames.Machina;
	}

	public int getCostToPlay() {
		return 4;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Machina";
	}

	public String getDescription() {
		return "The player picks up their building cards and lays them " +
				"again on any dice discs. Character cards can be covered.";
	}

	public int getDefense() {
		return 4;
	}


	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = true;
		
		List<Card> buildings = new ArrayList<Card>();
		for (Card c : g.getField().getSideAsList(g.whoseTurn())) {
			if (c.isBuilding()) {
				buildings.add(c);
			}
		}
		
		// remove the cards from the field
		for (Card c : buildings) {
			g.getField().removeCard(c);
		}
		
		// replace them
		Card selectedCard = null;
		while (buildings.size() > 0) {
			int dicePosition = -1;
			selectedCard = g.getController().getCard(buildings, "Select a character card to lay.");
			while (selectedCard == null) {
				selectedCard = g.getController().getCard(buildings, "Invalid card. Select a character card to lay.");
			}
			
			g.getController().showField();
			
			while (dicePosition < 1 || dicePosition > Game.FIELD_SIZE) {
				dicePosition = g.getController().getInt("Select a position to lay " + selectedCard.getName() + ":");
			}
			
			buildings.remove(selectedCard);
			g.getField().setCard(g.whoseTurn(), dicePosition-1, selectedCard);

		}
		
		return performed;


	}

}
