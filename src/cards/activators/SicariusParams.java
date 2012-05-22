package cards.activators;

import java.util.*;

import roma.*;
import cards.*;

public class SicariusParams extends CardParams {
	
	int targetPos;
	
	public SicariusParams() {
		
		targetPos = -1;
	
	}
	
	@Override
	public void query(GameVisor g, int pos) {

		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		List<Card> enemyField = g.getField().getSideAsList(enemy);
		
		List<Card> characters = new ArrayList<Card>();
		
		for (Card c : enemyField) {
			
			if (!c.isBuilding()) {
				
				characters.add(c);
				
			}
			
		}
		
		Card destroy = null;

		destroy = g.getController().getCard(characters, "Which opposing character card do you wish to eliminate?"); 
		
		if (destroy != null) {
		
			setTargetPos(g.getField().findCardPosition(destroy));
		
		}
	
	}
	
	/**
	 * Returns the field position of the targeted card [0-6]
	 * @return
	 */
	public int getTargetPos() {
	
		return targetPos;
	
	}
	
	public void setTargetPos(int pos) {
	
		targetPos = pos;
	
	}

	@Override
	public boolean isValid() {
	
		return targetPos != -1;
	
	}
	
}
