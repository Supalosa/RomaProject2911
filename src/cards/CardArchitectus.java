package cards;

import java.util.*;

import roma.*;
import enums.*;

public class CardArchitectus extends Card {

	public CardNames getID() {
		return CardNames.Architectus;
	}

	public int getCostToPlay() {
		return 3;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Architectus";
	}

	public String getDescription() {
		return "enables the player to lay as many building cards as they " +
				"wish free of charge. The player is allowed to cover any cards.";
	}

	public int getDefense() {
		return 4;
	}

	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		boolean stop = false;
		
		List<Card> buildings = new ArrayList<Card>();
		for (Card c : g.getCurrentPlayer().getHand()) {
			if (c.isBuilding()) {
				buildings.add(c);
			}
		}
		
		Card selectedCard = null;
		while (stop == false) {
			int dicePosition = -1;
			selectedCard = g.getController().getCard(buildings, "Select a building to lay. Enter negative to quit.");
			if (selectedCard == null) {
				stop = true;
			} else {
				performed = true;
				while (dicePosition < 1 || dicePosition > Game.FIELD_SIZE) {
					dicePosition = g.getController().getInt("Select a position to lay " + selectedCard.getName() + ":");
				}
				g.getCurrentPlayer().removeCard(selectedCard);
				g.getField().setCard(g.whoseTurn(), dicePosition-1, selectedCard);
			}
		}
		
		
		return performed;

	}

}
