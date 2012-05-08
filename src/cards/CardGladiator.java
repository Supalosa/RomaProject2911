package cards;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

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
	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos) {
		boolean activated = false;
		int enemyId = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		g.getController().showField();
		
		int targetPos = g.getController().getInt("Choose the enemy (dice disc) you want to return:");
		while (targetPos < 1 || targetPos > Game.FIELD_SIZE) {
			targetPos = g.getController().getInt("Invalid dice disc. Choose the Dice Disc you want to return");
		}
		Card targetCard = g.getField().getCard(enemyId,targetPos-1);
		if (targetCard != null) {
			g.getField().setCard(enemyId, targetPos-1, null);
			g.getPlayer(enemyId).addCard(targetCard);
			activated = true;
		}
		
		return activated;

	}

}
