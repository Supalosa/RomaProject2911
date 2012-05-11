package cards;

import java.util.ArrayList;
import java.util.List;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardVelites extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Velites;
	}

	@Override
	public int getCostToPlay() {
		return 5;
	}

	@Override
	public int getDiceToActivate() {
		return 1;
	}

	@Override
	public boolean isBuilding() {
		return false;
	}

	@Override
	public String getName() {
		return "Velites";
	}

	@Override
	public String getDescription() {
		return "Attacks any opposing character card (does not have to be directly opposite)." +
				" The battle die is thrown once.";
	}

	@Override
	public int getDefense() {
		return 3;
	}
	@Override
	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		List<Card> enemyField = g.getField().getSideAsList(enemy);
		
		List<Card> characters = new ArrayList<Card>();
		
		for (Card c : enemyField) {
			
			if (c.isBuilding()) {
				
				characters.add(c);
				
			}
			
		}
		
		Card target = null;
		
		while (target == null) {
			
			target = g.getController().getCard(characters, "Which opponent character card do you wish to attack?"); 
		
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
