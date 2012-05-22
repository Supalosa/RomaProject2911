package cards.activators;

import cards.Card;
import roma.Game;
import roma.GameVisor;

public class CenturioParams extends CardParams {

	private int battleDie;
	private boolean useExtraDice;
	private int extraDieValue;
	private boolean valid;

	public CenturioParams() {
		
		valid = true;
	
	}

	/**
	 * Returns the battle die that was specified to be used in the battle
	 * calculation.
	 * 
	 * This value can is pre-modification by Centurio's extra battle dice
	 * effect.
	 * 
	 * @return value of battleDie
	 */
	public int getBattleDie() {
	
		return battleDie;
	
	}

	/**
	 * Sets the battle die to be used for battle calculation.
	 * 
	 * Defining it here and keeping it constant makes acceptance testing
	 * possible (the result is kept constant) and also enables replays to have
	 * the exact same outcome.
	 * 
	 * @param dieValue
	 *            Dice value of the base battle value.
	 */
	public void setBattleDie(int dieValue) {
	
		battleDie = dieValue;
	
	}

	/**
	 * Returns whether an extra dice was used, for that extra oomph.
	 * 
	 * @return value of useExtraDice
	 */
	public boolean isUseExtraDice() {
		
		return useExtraDice;
	
	}

	/**
	 * Sets whether an extra dice will be used for the extra attack.
	 * 
	 * The value is only added in performEffect.
	 * 
	 * @param useExtraDice
	 *            Boolean representing whether the dice is to be used or not.
	 */
	public void setUseExtraDice(boolean useExtraDice) {
	
		this.useExtraDice = useExtraDice;
	
	}

	/**
	 * Gets the dice value that will be used if requested.
	 * 
	 * The battle value of the Centurio is increased by this amount if
	 * applicable.
	 * 
	 * @return value of extraDieValue
	 */
	public int getExtraDieValue() {
	
		return extraDieValue;
	
	}

	/**
	 * Sets the dice value that will be added to the Centurio's battle value if
	 * applicable.
	 * 
	 * @param extraDieValue
	 *            Face value of the dice to add
	 */
	public void setExtraDieValue(int extraDieValue) {
	
		this.extraDieValue = extraDieValue;
	
	}

	@Override
	public void query(GameVisor g, int pos) {

		valid = false;

		int enemyPlayer = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card enemyCard = g.getField().getCard(enemyPlayer, pos - 1);
		
		if (enemyCard != null) {
			
			valid = true;
			setBattleDie(g.rollDice());

			if (getBattleDie() < enemyCard.getRealDefense()) {
				useExtraDice = g.getController().getBoolean(
						"Do you wish to add the the value of the battle die?");

				if (useExtraDice) {
					
					int diceValue = 0;
					
					while (diceValue == 0) {
					
						g.getController().showDiceRolls();
						int add = g.getController().getInt(
								"Which die do you want to use?");

						if (g.hasDiceRoll(add)) {
						
							extraDieValue = add;
						
						}
					
					}
					
					setBattleDie(getBattleDie() + getExtraDieValue());
				
				}

			}
		} else {

			setError("There is nothing to attack.");

		}
		
	}

	@Override
	public boolean isValid() {
		
		return valid;
	
	}

}
