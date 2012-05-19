package cards.activators;

import java.util.*;

import roma.*;
import cards.*;

public class NeroParams extends CardParams {
	
	int targetPos;
	
	public NeroParams() {
		targetPos = -1;
	}
	
	@Override
	public void query(GameVisor g, int pos) {

		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		List<Card> enemyField = g.getField().getSideAsList(enemy);
		
		List<Card> buildings = new ArrayList<Card>();
		
		for (Card c : enemyField) {
			
			if (c.isBuilding()) {
				
				buildings.add(c);
				
			}
			
		}
		
		Card destroy = null;

		destroy = g.getController().getCard(buildings, "Which opposing building card do you wish to eliminate?"); 
		
	
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
