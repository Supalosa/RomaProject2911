package cards.activators;

import java.util.*;

import roma.*;
import cards.*;

public class ConsiliariusParams extends CardParams {
	
	/**
	 * Map of old position->new position
	 */
	Queue<PositionMapping> cardPositions;
	
	public ConsiliariusParams() {
		
		cardPositions = new LinkedList<PositionMapping>();
		
	}
	
	@Override
	public void query(GameVisor g, int pos) {

		int dicePosition;
		
		g.getController().showField();
		
		for (int fieldPos = 0; fieldPos < Game.FIELD_SIZE; fieldPos++) {
			Card c = g.getField().getCard(g.whoseTurn(), fieldPos);
			if (!c.isBuilding()) {
				do {
					dicePosition = g.getController().getInt("Select a position to lay " + c.getName() + ":");
				} while (dicePosition < 1 || dicePosition > Game.FIELD_SIZE);
				
				addPosition(fieldPos, dicePosition-1);
			}
		}
		
		if (cardPositions.size() == 0) {
			setError("No cards played.");
		}

	}
	
	/**
	 * Set field position 'old_pos' to be put in position 'new_pos' when action is performed.
	 * @param old_pos the position of the card in the hand
	 * @param new_pos new position of this card in terms of array indices [0-7]
	 */
	public void addPosition(int old_pos, int new_pos) {
		cardPositions.add(new PositionMapping(old_pos, new_pos));
	}
	
	public PositionMapping getNextPosition() {
		return cardPositions.poll();
	}
	
	public boolean isValid() {
		return cardPositions.size() > 0;
	}


	
	
}
