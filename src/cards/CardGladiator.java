package cards;

import roma.Game;
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
		return EffectTrigger.TriggerOnPlay;
	}

	@Override
	public boolean performEffect(Game g) {
		return false;
		// TODO Auto-generated method stub

	}

}
