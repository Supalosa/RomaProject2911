package cards.activators;

import java.util.*;

import roma.*;
import cards.*;

public class SenatorParams extends CardParams {
	
	/**
	 * Map of Card (in hand)->new position
	 */
	Map<Card, Integer> cardPositions;
	
	public SenatorParams() {
		
		cardPositions = new HashMap<Card, Integer>();
		
	}
	
	@Override
	public void query(GameVisor g, int pos) {

		int handIndex = 0;
		int dicePosition;
		for (Card c : g.getCurrentPlayer().getHand()) {
			if (!c.isBuilding()) {
				do {
					dicePosition = g.getController().getInt("Select a position to lay " + c.getName() + ": (0 to skip)");
				} while (dicePosition < 0 || dicePosition > Game.FIELD_SIZE);
				
				if (dicePosition != 0) {
					addPosition(c, dicePosition);
				}
			}
			
			handIndex ++;
		}

		if (cardPositions.size() == 0) {
			setError("No cards played.");
		}
	}
	
	/**
	 * Set hand position 'hand' to be put in position 'new_pos' when action is performed.
	 * @param hand the position of the card in the hand
	 * @param new_pos new position of this card in terms of array indices [0-7]
	 */
	public void addPosition(Card card, int new_pos) {
		cardPositions.put(card, new_pos);
	}
	
	public Map<Card, Integer> getPositions() {
		return cardPositions;
	}
	
	public boolean isValid() {
		return cardPositions.size() > 0;
	}

	
	
}
