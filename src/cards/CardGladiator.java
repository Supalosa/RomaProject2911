package cards;

import cards.activators.CardParams;
import cards.activators.GladiatorParams;
import roma.*;
import enums.*;

public class CardGladiator extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Gladiator;
	}

	@Override
	public int getCostToPlay() {
		return 6;
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
		return "Gladiator";
	}

	@Override
	public String getDescription() {
		return "An opponent's face-up character card (chosen by the player" +
				" whose turn it is) must be returned to the opponent's hand.";
	}

	@Override
	public int getDefense() {
		return 5;
	}


	@Override
	public CardParams getParams() {
		return new GladiatorParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		GladiatorParams myParams = (GladiatorParams) a;
		boolean activated = false;
		int enemyId = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;


		int targetPos = myParams.getPositionToAttack();
		
		Card targetCard = g.getField().getCard(enemyId,targetPos);

		if (targetCard != null) {

			if (!targetCard.isBuilding()) {

				g.getField().setCard(enemyId, targetPos, null);
				g.getPlayer(enemyId).addCard(targetCard);
				activated = true;

			} else {

				g.getController().showMessage("Not a Character card");

			}
		} else {
			// time paradox?
		}

		return activated;
	}

}
