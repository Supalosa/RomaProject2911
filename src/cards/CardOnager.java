package cards;

import java.util.ArrayList;
import java.util.List;

import enums.*;

import roma.*;

public class CardOnager extends Card {
	
	public CardNames getID() {
		return CardNames.Onager;
	}

	public int getCostToPlay() {
		return 5;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Onager";
	}

	public String getDescription() {
		return "This Roman cata- pult attacks any opposing building. " +
				"The battle die is thrown once.";
	}

	public int getDefense() {
		return 4;
	}

	public EffectTrigger getEffectTrigger() {
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
		
		Card target = null;
		
		while (target == null) {
			
			target = g.getController().getCard(buildings, "Which opposing building card do you wish to attack?"); 
		
		}
		
		int diceRoll = g.rollDice();
		g.getController().showMessage("The battle die rolled a " + diceRoll);
		
		if (diceRoll >= target.getDefense()) {
			
			g.getField().setCard(enemy, enemyField.indexOf(target), null);
			g.discard(target);
			g.getController().showMessage("You killed a " + target.getName() + "!");
			
		} else {
			
			g.getController().showMessage("You're weak...");
			
		}
		
		return performed;

	}


}
