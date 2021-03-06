package cards.activators;

import java.util.ArrayList;
import java.util.List;

import roma.Game;
import roma.GameVisor;

public class ConsulParams extends CardParams {

	private boolean isValid;
	private boolean increaseDice;
	private int diceValue;

	public ConsulParams() {

		isValid = true;

	}

	/**
	 * Returns whether this Consul is set to increase die by 1, or decrease die
	 * by 1. True indicates increase. False indictes decrease.
	 * 
	 * @return value of increaseDice
	 */
	public boolean isIncreaseDice() {

		return increaseDice;

	}

	/**
	 * Sets whether this Consul should increase or decrease the die. True
	 * indicates Increase, False indicates Decrease.
	 * 
	 * @param increaseDice
	 */
	public void setIncreaseDice(boolean increaseDice) {

		this.increaseDice = increaseDice;

	}

	/**
	 * Gets the face value of the dice that should be affected.
	 * 
	 * @return value of diceValue
	 */
	public int getDiceValue() {

		return diceValue;

	}

	/**
	 * Sets the dice that will be affected by activation of the Consul.
	 * 
	 * @param diceValue
	 *            face value of the dice
	 */
	public void setDiceValue(int diceValue) {

		this.diceValue = diceValue;

	}

	@Override
	public void query(GameVisor g, int pos) {

		isValid = false;

		if (g.getNumDiceRolls() > 1) {
			// Build a list of dice rolls that exclude the one that activated
			// this card.
			List<Integer> diceRolls = new ArrayList<Integer>();
			boolean removedActivator = false;
			int roll;

			for (int i = 0; i < Game.NUM_DIE; i++) {

				roll = g.getDiceRoll(i);

				if (roll > 0) { // dice was not used

					if (removedActivator || roll != pos) {

						diceRolls.add(roll);

					} else {

						removedActivator = true;

					}

				}

			}

			g.getController().showMessage(
					"Available dice: " + diceRolls.toString());

			int diceRoll;
			boolean increase;
			diceRoll = g.getController().getInt(
					"Enter of the value of the dice roll you want to change:");

			while (diceRolls.contains(diceRoll) == false) {
				diceRoll = g
						.getController()
						.getInt("You don't have that die. Enter of the value of the dice roll you want to change:");

			}

			setDiceValue(diceRoll);

			increase = g
					.getController()
					.getBoolean(
							"Do you want to increase (Y) or decrease (N) the value of that die?");

			if (increase && diceRoll == 6) {

				setError("You cannot increase the value of a 6 die.");

			} else if (!increase && diceRoll == 1) {

				setError("You cannot decrease the value of a 1 die.");

			} else {

				isValid = true;
				setIncreaseDice(increase);

			}

		} else {

			setError("You cannot activate this card, because you will not have any dice available.");

		}

	}

	@Override
	public boolean isValid() {

		return isValid;

	}

}
