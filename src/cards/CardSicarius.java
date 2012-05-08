package cards;

import java.util.ArrayList;
import java.util.List;

import roma.Game;
import roma.GameVisor;
import enums.*;

public class CardSicarius extends Card {

	public CardNames getID() {
		return CardNames.Sicarius;
	}

	public String getName() {
		return "Sicarius";
	}
	
	public int getCostToPlay() {
		return 9;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}


	public String getDescription() {
		return "eliminates an opposing, face-up character card. " +
				"The opposing card and the Sicarius are both discarded";
	}

	public int getDefense() {
		return 2;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		List<Card> enemyField = g.getField().getSideAsList(enemy);
		
		List<Card> characters = new ArrayList<Card>();
		
		for (Card c : enemyField) {
			
			if (!c.isBuilding()) {
				
				characters.add(c);
				
			}
			
		}
		
		Card destroy = null;
		
		while (destroy == null) {
			
			destroy = g.getController().getCard(characters, "Which opposing character card do you wish to eliminate?"); 
		
		}
		
		g.getField().setCard(g.whoseTurn(), pos, null);
		g.getField().setCard(enemy, enemyField.indexOf(destroy), null);
		g.discard(destroy);
		g.discard(this);
		
		performed = true;
		
		return performed;

	}

}
