package cards;

import roma.Game;
import enums.CardNames;
import enums.EffectTrigger;

public class CardMercator extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Mercator;
	}

	@Override
	public int getCostToPlay() {
		return 7;
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
		return "Mercator";
	}

	@Override
	public String getDescription() {
		return "For 2 sestertii each, the player can buy 1 victory point from their opponent as long " +
				"as there are money and victory points left! The opponent gets the money";
	}

	@Override
	public int getDefense() {
		return 2;
	}

	@Override
	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerNone;
	}

	@Override
	public boolean performEffect(Game g) {
		return false;
		// TODO Auto-generated method stub

	}

}
