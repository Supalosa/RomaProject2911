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
	
	public int getBattleDie() {
		return battleDie;
	}
	
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
