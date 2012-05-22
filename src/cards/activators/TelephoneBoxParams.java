package cards.activators;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import roma.GameVisor;

public class TelephoneBoxParams extends CardParams {

	private int posToSend; // which dice disc to send back in time
	private boolean goForward;
	private int diceToUse; // which dice to use the value of (go forward or
							// backwards)

	private boolean valid;

	public TelephoneBoxParams() {

		valid = true;

	}

	/**
	 * Gets the field position that is to be sent into the past or future.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @return value of diceToSend
	 */
	public int getPosToSend() {
		return posToSend;
	}

	/**
	 * Sets the field position that is to be sent into the past or future.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @param posToSend
	 *            the field position of the card to time travel.
	 */
	public void setPosToSend(int posToSend) {
		this.posToSend = posToSend;
	}

	/**
	 * Returns whether the card should go forward or backward in time.
	 * 
	 * True indicates this card goes forward in time. False indicates this card
	 * goes backward in time.
	 * 
	 * @return value of goForward
	 */
	public boolean isGoForward() {

		return goForward;

	}

	/**
	 * Specifies whether the card should go forward or backward in time.
	 * 
	 * True indicates this card goes forward in time. False indicates this card
	 * goes backward in time.
	 * 
	 * @return value of goForward
	 */
	public void setGoForward(boolean goForward) {

		this.goForward = goForward;

	}

	/**
	 * Gets the dice value that will be used to determine how many turns
	 * forward/backward to go.
	 * 
	 * @return value of diceToSend
	 */
	public int getDiceToUse() {

		return diceToUse;

	}

	/**
	 * Sets the dice value that will be used to determine how many turns
	 * forward/backward to go.
	 * 
	 * @param diceToUse
	 *            Dice value of the dice to determine amplitude of TelephoneBox
	 */
	public void setDiceToUse(int diceToUse) {

		this.diceToUse = diceToUse;

	}

	@Override
	public void query(GameVisor g, int pos) {

		valid = false;

		boolean sendForward;

		List<Card> myField = g.getField().getSideAsList(g.whoseTurn());

		List<Card> characters = new ArrayList<Card>();

		for (Card c : myField) {

			characters.add(c);

		}

		Card target = null;

		target = g.getController().getCard(characters,
				"Which card do you wish to travel in time?");

		if (target != null) {

			setPosToSend(g.getField().findCardPosition(target));

			valid = true;

			sendForward = g.getController().getBoolean(
					"Do you wish to go forward (Y) or back (N) in time?");
			setGoForward(sendForward);

			g.getController().showDiceRolls();
			int add = g.getController().getInt("Which die do you want to use?");

			if (g.hasDiceRoll(add)) {

				setDiceToUse(add);

			}

		}

	}

	@Override
	public boolean isValid() {

		return valid;

	}

}
