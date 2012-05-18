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
	
	public int getBattleDie() {
		return battleDie;
	}
	
	public void setBattleDie(int dieValue) {
		battleDie = dieValue;
	}
	
	
	public boolean isUseExtraDice() {
		return useExtraDice;
	}

	public void setUseExtraDice(boolean useExtraDice) {
		this.useExtraDice = useExtraDice;
	}

	public int getExtraDieValue() {
		return extraDieValue;
	}

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
				useExtraDice = g.getController().getBoolean("Do you wish to add the the value of the battle die?");
				
				if (useExtraDice) {
					int diceValue = 0;
					while (diceValue == 0) {
						g.getController().showDiceRolls();
						int add = g.getController().getInt("Which die do you want to use?");
						
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
