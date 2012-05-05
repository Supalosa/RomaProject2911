package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardScaenicus extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Scaenicus;
	}

	@Override
	public int getCostToPlay() {
		return 8;
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
		return "Scaenicus";
	}

	@Override
	public String getDescription() {
		return "He performs no action of his own but can copy the action of any " +
				"of the player's own face-up character cards, and the next time round" +
				" that of another.";
	}

	@Override
	public int getDefense() {
		return 3;
	}

	@Override
	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	@Override
	public boolean performEffect(GameVisor g) {
		return false;
		// TODO Auto-generated method stub

	}

}
