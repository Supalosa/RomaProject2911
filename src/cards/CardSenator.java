package cards;

import java.util.ArrayList;
import java.util.List;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardSenator extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Senator;
	}

	@Override
	public int getCostToPlay() {
		return 3;
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
		return "Senator";
	}

	@Override
	public String getDescription() {
		return "enables the player to lay as many character cards as they wish free of charge. " +
				"The player is allowed to cover any cards.";
	}

	@Override
	public int getDefense() {
		return 3;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos) {
		boolean performed = false;
		boolean stop = false;
		
		List<Card> characters = new ArrayList<Card>();
		for (Card c : g.getCurrentPlayer().getHand()) {
			if (!c.isBuilding()) {
				characters.add(c);
			}
		}
		
		Card selectedCard = null;
		while (stop == false) {
			int dicePosition = -1;
			selectedCard = g.getController().getCard(characters, "Select a character card to lay. Enter negative to quit.");
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
