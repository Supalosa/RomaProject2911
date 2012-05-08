package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardVelites extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Velites;
	}

	@Override
	public int getCostToPlay() {
		return 5;
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
		return "Velites";
	}

	@Override
	public String getDescription() {
		return "Attacks any opposing character card (does not have to be directly opposite)." +
				" The battle die is thrown once.";
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
	public boolean performEffect(GameVisor g, int pos) {
		// TODO Auto-generated method stub
		return false;
	}

}
