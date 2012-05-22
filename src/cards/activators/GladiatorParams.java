package cards.activators;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import roma.Game;
import roma.GameVisor;

public class GladiatorParams extends CardParams {

	private int positionToAttack;
	private boolean valid;
	
	public GladiatorParams() {
		
		valid = true;
	
	}
	

	public int getPositionToAttack() {
	
		return positionToAttack;
	
	}
	
	/**
	 * Sets the field position to attack (in terms of 0-6)
	 * @param pos
	 */
	public void setPositionToAttack(int pos) {
	
		this.positionToAttack = pos;
	
	}
	
	@Override
	public void query(GameVisor g, int pos) {
		
		valid = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		List<Card> enemyField = g.getField().getSideAsList(enemy);
		
		List<Card> characters = new ArrayList<Card>();
		
		for (Card c : enemyField) {
			
			if (!c.isBuilding()) {
				
				characters.add(c);
				
			}
			
		}
		
		Card target = null;
		
		target = g.getController().getCard(characters, "Which opposing character card do you wish to attack?"); 
		
		if (target != null) {
		
			setPositionToAttack(g.getField().findCardPosition(target));
			valid = true;
		
		}
		
	}

	@Override
	public boolean isValid() {
	
		return valid;
	
	}
	
}
