package cards;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardCenturio extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Centurio;
	}

	@Override
	public int getCostToPlay() {
		return 9;
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
		return "Centurio";
	}

	@Override
	public String getDescription() {
		return "Attacks the card directly opposite, whether it is a character or building card. " +
				"The value of an unused action die can be added to the value of the battle die " +
				"(the action die is then counted as used). This is decided after the battle " +
				"die has been thrown.";
	}

	@Override
	public int getDefense() {
		return 5;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		int enemyPlayer = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card enemyCard = g.getField().getCard(enemyPlayer, pos - 1);
		if (enemyCard != null) {
			
			int battleDie = g.rollDice();
			g.getController().showMessage("The battle die rolled a " + battleDie);
			g.useDice(pos);

			if (battleDie < enemyCard.getRealDefense()) {

				if (g.getController().getBoolean("Do you wish to add the the value of the battle die?")) {
					int diceValue = 0;
					while (diceValue == 0) {
						g.getController().showDiceRolls();
						int add = g.getController().getInt("Which die do you want to use?");
						
						for (int i = 0; i < g.getDiceRolls().length && diceValue == 0; i++) {
							if (add == g.getDiceRoll(i)) {
								diceValue = g.getDiceRoll(i);
							}
						}
					}
					g.useDice(diceValue);
					battleDie += diceValue;
				}
				
			}
			
			// successfully killed the card
			if (battleDie >= enemyCard.getRealDefense()) {
				g.discard(enemyCard);
				g.getField().setCard(enemyPlayer, pos-1, null);
				g.getController().showMessage("You killed a " + enemyCard.getName() + "!");
			}
			
			
			
		} else {
			
			g.getController().showMessage("There is nothing to attack.");
			
		}
		
		return performed;
	}

}
