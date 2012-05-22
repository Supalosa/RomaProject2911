package cards.activators;

import cards.Card;
import roma.Game;
import roma.GameVisor;

public class LegionariusParams extends CardParams {

	private int battleDie;
	private boolean valid;
	
	public LegionariusParams() {
		
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

	@Override
	public void query(GameVisor g, int pos) {
		
		valid = false;
		
		int enemyPlayer = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card enemyCard = g.getField().getCard(enemyPlayer, pos - 1);
		
		if (enemyCard != null) {
		
			valid = true;
			setBattleDie(g.rollDice());			
			
		} else {
			
			setError("There is nothing to attack.");
			
		}
		
	}

	@Override
	public boolean isValid() {
		
		return valid;
	
	}

}
