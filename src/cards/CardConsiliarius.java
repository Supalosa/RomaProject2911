package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardConsiliarius extends Card {

	public CardNames getID() {
		return CardNames.Consiliarius;
	}


	public int getCostToPlay() {
		return 4;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Consiliarius";
	}

	public String getDescription() {
		return "The player picks up their character cards and can then lay them " +
		"again on any dice disc. Buildings can be covered.";
	}

	public int getDefense() {
		return 4;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g) {
		return false;
		// TODO Auto-generated method stub

	}

}
