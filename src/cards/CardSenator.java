package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

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
		return "enables the player to lay as many character cards as they wish free of charge. " +
				"The player is allowed to cover any cards.";
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
		// TODO Auto-generated method stub
		return false;
	}

}
