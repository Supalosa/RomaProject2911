package cards.activators;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import roma.Game;
import roma.GameVisor;

public class OnagerParams extends CardParams {

	private int battleDie;
	private int positionToAttack;
	private boolean valid;
	
	public OnagerParams() {
		
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
	 * Gets the Field Position that should be attacked by this card.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * @return value of positionToAttack
	 */
	public int getPositionToAttack() {
	
		return positionToAttack;
	
	}
	
	/**
	 * Sets the field position to attack.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * @param pos the field position this Gladiator should attack
	 */
	public void setPositionToAttack(int pos) {
	
		this.positionToAttack = pos;
	
	}
	
	@Override
	public void query(GameVisor g, int pos) {
		
		valid = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		List<Card> enemyField = g.getField().getSideAsList(enemy);
		
		List<Card> buildings = new ArrayList<Card>();
		
		for (Card c : enemyField) {
			
			if (c.isBuilding()) {
				
				buildings.add(c);
				
			}
			
		}
		
		Card target = null;
		
		target = g.getController().getCard(buildings, "Which opposing building card do you wish to attack?"); 
		
		if (target != null) {
			
			setPositionToAttack(g.getField().findCardPosition(target));
			int diceRoll = g.rollDice();
			
			setBattleDie(diceRoll);
			valid = true;
		
		}
		
	}

	@Override
	public boolean isValid() {
		
		return valid;
	
	}

}
