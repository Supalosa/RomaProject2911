package cards.activators;

import java.util.*;

import roma.*;
import cards.*;

public class SenatorParams extends CardParams {

	/**
	 * Map of Card (in hand)->new position
	 */
	private List<CardMapping> cardPositions;

	/**
	 * Senator stores a map between CardNames and the positions it should go to.
	 */
	public SenatorParams() {

		cardPositions = new ArrayList<CardMapping>();

	}

	@Override
	public void query(GameVisor g, int pos) {

		int dicePosition;
		for (Card c : g.getCurrentPlayer().getHand()) {
			if (!c.isBuilding()) {
				do {
					dicePosition = g.getController().getInt(
							"Select a position to lay " + c.getName()
									+ ": (0 to skip)");
				} while (dicePosition < 0 || dicePosition > Game.FIELD_SIZE);

				if (dicePosition != 0) {
					addPosition(c, dicePosition);
				}
			}

		}

		if (cardPositions.size() == 0) {
			setError("No cards played.");
		}
	}

	/**
	 * Indicates that the first instance of the card 'card' is to be put in
	 * position 'new_pos' when Senator.performEffect is performed.
	 * 
	 * @param card
	 *            Instance of the card that was mapped. Only the getID() value
	 *            is used.
	 * @param new_pos
	 *            new position of this card in terms of array indices [0-7]
	 */
	public void addPosition(Card card, int new_pos) {
		cardPositions.add(new CardMapping(card.getID(), new_pos));
	}

	/**
	 * Returns the mutable list of CardMappings in this parameter
	 * 
	 * <b>WARNING:</b> This is mutable, so modifying the list that is returned
	 * is NOT advised. You <u>will</u> break time travel and replay ability;
	 * 
	 * @return value of cardPositions
	 */
	public List<CardMapping> getPositions() {
		return cardPositions;
	}

	public boolean isValid() {
		return cardPositions.size() > 0;
	}

}
