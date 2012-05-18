package cards;

import cards.activators.CardParams;
import cards.activators.CenturioParams;
import cards.activators.LegionariusParams;
import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardLegionarius extends Card {

	public CardNames getID() {
		return CardNames.Legionarius;
	}

	public int getCostToPlay() {
		return 4;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Legionarius";
	}

	public String getDescription() {
		return "Attacks the opponent's card which is directly opposite, " +
		"whether it is a character or a building card.";
	}

	public int getDefense() {
		return 5;
	}

	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		int enemyPlayer = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card enemyCard = g.getField().getCard(enemyPlayer, pos - 1);
		if (enemyCard != null) {
			
			int battleDie = g.rollDice();
			g.getController().showMessage("The battle die rolled a " + battleDie);

			// successfully killed the card
			if (battleDie >= enemyCard.getRealDefense()) {
			
				g.discard(enemyCard);
				g.getField().setCard(enemyPlayer, pos-1, null);
				g.getController().showMessage("You killed a " + enemyCard.getName() + "!");
			
			} else {
				
				g.getController().showMessage("You're too weak...");
			
			}
			
			performed = true;
			
		} else {
			
			g.getController().showMessage("There is nothing to attack.");
			
		}
		
		return performed;
		
	}

	@Override
	public CardParams getParams() {
		return new LegionariusParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		LegionariusParams myParams = (LegionariusParams)a;
		
		boolean performed = false;
		
		int enemyPlayer = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card enemyCard = g.getField().getCard(enemyPlayer, pos-1);
		// determine time paradox here
		
		if (enemyCard == null) {
			// time paradox
		} else {
			// successfully killed the card?
			performed = true;

			if (myParams.getBattleDie() >= enemyCard.getRealDefense()) {
				g.discard(enemyCard);
				g.getField().setCard(enemyPlayer, pos-1, null);
				g.getController().showMessage("You killed a " + enemyCard.getName() + "!");
			}
		}
		return performed;
	}

}
