package cards.activators;

import java.util.*;

import roma.*;
import cards.*;

public class MachinaParams extends CardParams {

	/**
	 * Map of old position->new position
	 */
	Queue<PositionMapping> cardPositions;

	public MachinaParams() {

		cardPositions = new LinkedList<PositionMapping>();

	}

	@Override
	public void query(GameVisor g, int pos) {

		int dicePosition;

		g.getController().showField();

		for (int fieldPos = 0; fieldPos < Game.FIELD_SIZE; fieldPos++) {

			Card c = g.getField().getCard(g.whoseTurn(), fieldPos);

			if (c.isBuilding()) {

				do {

					dicePosition = g.getController().getInt(
							"Select a position to lay " + c.getName() + ":");

				} while (dicePosition < 1 || dicePosition > Game.FIELD_SIZE);

				addPosition(fieldPos, dicePosition - 1);

			}

		}

		if (cardPositions.size() == 0) {

			setError("No cards played.");

		}

	}

	/**
	 * Set field position 'old_pos' to be put in field position 'new_pos' when
	 * action is performed.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @param old_pos
	 *            the field position of the card in the hand
	 * @param new_pos
	 *            new field position of this card in terms of array indices
	 *            [0-7]
	 */
	public void addPosition(int old_pos, int new_pos) {

		cardPositions.add(new PositionMapping(old_pos, new_pos));

	}

	/**
	 * Returns the mutable list of PositionMappings in this parameter
	 * 
	 * <b>WARNING:</b> This is mutable, so modifying the list that is returned
	 * is NOT advised. You <u>will</u> break time travel and replay ability;
	 * 
	 * @return value of cardPositions
	 */
	public List<PositionMapping> getMappings() {

		return new ArrayList<PositionMapping>(cardPositions);

	}

	public boolean isValid() {

		return cardPositions.size() > 0;

	}

}
