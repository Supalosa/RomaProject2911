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
	
	public int getBattleDie() {
		return battleDie;
	}
	
	public void setBattleDie(int dieValue) {
		battleDie = dieValue;
	}

	public int getPositionToAttack() {
		return positionToAttack;
	}
	
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
