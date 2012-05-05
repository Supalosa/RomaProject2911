package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardConsul extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Consul;
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
		return "Consul";
	}

	@Override
	public String getDescription() {
		return  "The score on an action die which has not yet been used can be " +
				"increased or decreased by 1 point.";
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
