package cards.activators;

import roma.*;

public class PraetorianusParams extends CardParams {

	private int positionToAttack;
	private boolean valid;

	public PraetorianusParams() {

		valid = true;

	}

	/**
	 * Gets the Field Position that should be blocked by this card.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @return value of positionToAttack
	 */
	public int getPositionToAttack() {

		return positionToAttack;

	}

	/**
	 * Sets the field position to blocked.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @param pos
	 *            the field position this Praetorianus should block
	 */
	public void setPositionToAttack(int pos) {

		this.positionToAttack = pos;

	}

	@Override
	public void query(GameVisor g, int pos) {

		valid = false;

		g.getController().showField();

		int diceDisc = g.getController().getInt(
				"Which dice disc do you wish to block?");

		if (diceDisc < 1 || diceDisc > 7) {

			setError("Invalid dice disc. Which dice disc do you wish to block?");

		} else {

			valid = true;
			setPositionToAttack(diceDisc - 1);

		}

	}

	@Override
	public boolean isValid() {

		return valid;

	}

}
