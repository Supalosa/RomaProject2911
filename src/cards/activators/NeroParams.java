package cards.activators;

import java.util.*;

import roma.*;
import cards.*;

public class NeroParams extends CardParams {

	int targetPos;

	public NeroParams() {

		targetPos = -1;

	}

	@Override
	public void query(GameVisor g, int pos) {

		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;

		List<Card> enemyField = g.getField().getSideAsList(enemy);

		List<Card> buildings = new ArrayList<Card>();

		for (Card c : enemyField) {

			if (c.isBuilding()) {

				buildings.add(c);

			}

		}

		Card destroy = null;

		destroy = g.getController().getCard(buildings,
				"Which opposing building card do you wish to eliminate?");

		if (destroy != null) {

			setTargetPos(g.getField().findCardPosition(destroy));

		}

	}

	/**
	 * Returns the field position of the targeted card to attack.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @return value of targetPos
	 */
	public int getTargetPos() {

		return targetPos;

	}

	/**
	 * Sets the target field position of Nero.
	 * 
	 * It is possible that this may erroneously point to a Character Card due to
	 * time travel, but this is not a time paradox. The Nero still dies.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @param pos
	 *            The field position of the target
	 */
	public void setTargetPos(int pos) {

		targetPos = pos;

	}

	@Override
	public boolean isValid() {

		return targetPos != -1;

	}

}
