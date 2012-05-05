package cards;

import java.util.*;

import roma.*;
import enums.*;
import actions.*;

public class CardArchitectus extends Card {

	public CardNames getID() {
		return CardNames.Architectus;
	}

	public int getCostToPlay() {
		return 3;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Architectus";
	}

	public String getDescription() {
		return "enables the player to lay as many building cards as they " +
				"wish free of charge. The player is allowed to cover any cards.";
	}

	public int getDefense() {
		return 4;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g) {
		
		boolean performed = false;
		boolean finished = false;
		
		List<Card> characters = new ArrayList<Card>();
		
		for (Card c : g.getCurrentPlayer().getHand()) {
			
			if (!c.isBuilding()) {
				
				characters.add(c);
				g.getCurrentPlayer().getHand().remove(c);
				
			}
			
		}
		
		PlayerAction layCards = new LayCardAction();
		
		while ((g.getCurrentPlayer().getHandSize() > 0) && (!finished)) {
			
			
			layCards.execute(g);
			
			performed = true;
			
		}
		
		return performed;

	}

}
