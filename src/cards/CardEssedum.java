package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardEssedum extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Essedum;
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
		return "Essedum";
	}

	@Override
	public String getDescription() {
		return "The defence value of the opponent's face-up cards is reduced by 2.";
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
		// To be implemented by RG after SpellAuras implemented
		return false;
	}

}
