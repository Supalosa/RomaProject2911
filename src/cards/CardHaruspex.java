package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardHaruspex extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Haruspex;
	}

	@Override
	public int getCostToPlay() {
		return 4;
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
		return "Haruspex";
	}

	@Override
	public String getDescription() {
		return "The player can choose any card from the pile of face-down cards and add " +
				"it to their hand. Afterwards the pile is shuffled.";
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
	}

}
