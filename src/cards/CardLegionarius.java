package cards;

import cards.activators.CardParams;
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

		return "Attacks the opponent's card which is directly opposite, "
				+ "whether it is a character or a building card.";

	}

	public int getDefense() {

		return 5;

	}

	@Override
	public CardParams getParams() {

		return new LegionariusParams();

	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {

		LegionariusParams myParams = (LegionariusParams) a;

		boolean performed = false;

		int enemyPlayer = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card enemyCard = g.getField().getCard(enemyPlayer, pos - 1);
		// determine time paradox here

		if (enemyCard == null) {
			// not a time paradox
		} else {
			// successfully killed the card?
			performed = true;

			if (enemyCard.onAttacked(g, this, pos - 1, myParams.getBattleDie())) {

				g.getController().showMessage("You killed a " + enemyCard.getName() + "! battle value was "
											+ myParams.getBattleDie() + "/" + enemyCard.getRealDefense());

			} else {

				g.getController().showMessage("Could not kill the " + enemyCard.getName() + ", battle value was "
												+ myParams.getBattleDie() + "/" + enemyCard.getRealDefense());

			}

		}

		return performed;

	}

}
