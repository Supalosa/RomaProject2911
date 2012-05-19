package cards;

import cards.activators.CardParams;
import cards.activators.CenturioParams;
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
	public CardParams getParams() {
		return new CenturioParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		CenturioParams myParams = (CenturioParams)a;
		boolean performed = false;
		int enemyPlayer = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card enemyCard = g.getField().getCard(enemyPlayer, pos-1);
		int battleValue = myParams.getBattleDie();
		// determine time paradox here
		
		if (enemyCard == null) {
			// time paradox
		} else {
			// successfully killed the card?
			performed = true;
			if (myParams.isUseExtraDice()) {
				g.useDice(myParams.getExtraDieValue());
				battleValue += myParams.getExtraDieValue();
			}
			
			if (enemyCard.onAttacked(g, this, pos-1, battleValue)) {

				g.getController().showMessage("You killed a " + enemyCard.getName() + "!");
				
			} else {
				
				g.getController().showMessage("Could not kill the target, battle value was " + battleValue);
				
			}
		}
		
		return performed;
	}

}
