package cards;

import java.util.*;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardNero extends Card {

	public CardNames getID() {
		return CardNames.Nero;
	}

	public int getCostToPlay() {
		return 8;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Nero";
	}

	public String getDescription() {
		return "Destroys any face-up opposing building card. " +
				"The destroyed card and Nero are both discarded.";
	}

	public int getDefense() {
		// TODO Auto-generated method stub
		return 9;
	}

	public EffectTrigger getEffectTrigger() {
		// TODO Auto-generated method stub
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		List<Card> enemyField = g.getField().getSideAsList(enemy);
		
		List<Card> buildings = new ArrayList<Card>();
		
		for (Card c : enemyField) {
			
			if (c.isBuilding()) {
				
				buildings.add(c);
				
			}
			
		}
		
		Card destroy = null;
		
		while (destroy == null) {
			
			destroy = g.getController().getCard(buildings, "Which opposing building card do you wish to destroy?"); 
		
		}
		
		g.getField().setCard(g.whoseTurn(), pos, null);
		g.getField().setCard(enemy, enemyField.indexOf(destroy), null);
		g.discard(destroy);
		g.discard(this);
		
		performed = true;
		
		return performed;

	}

}
