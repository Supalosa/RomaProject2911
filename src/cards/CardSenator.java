package cards;

import cards.activators.*;

import roma.*;
import enums.CardNames;

public class CardSenator extends Card {

	@Override
	public CardNames getID() {

		return CardNames.Senator;

	}

	@Override
	public int getCostToPlay() {

		return 3;

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

		return "Senator";

	}

	@Override
	public String getDescription() {

		return "enables the player to lay as many character cards as they wish free of charge. "
				+ "The player is allowed to cover any cards.";

	}

	@Override
	public int getDefense() {

		return 3;

	}

	/**
	 * Performs the effect of this Senator using the passed-in Senator
	 * Parameters
	 */
	public boolean performEffect(GameVisor g, int pos, CardParams a) {

		SenatorParams myParams = (SenatorParams) a;
		boolean performed = true;
		Card laidOverCard;

		for (CardMapping mappings : myParams.getPositions()) {
			Card theCard = g.getCurrentPlayer().getCard(
					mappings.getInitialCard());

			// Remove card from their hand
			g.getCurrentPlayer().removeCard(theCard);

			// Add the card to the field, discarding replaced card if necessary

			if ((laidOverCard = g.getField().setCard(g.whoseTurn(),
					mappings.getFinalPos() - 1, theCard)) != null) {

				g.discard(laidOverCard);

			}
		}

		return performed;

	}

	@Override
	public CardParams getParams() {

		return new SenatorParams();

	}

}
