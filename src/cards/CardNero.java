package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardNero extends Card {

	public CardNames getID() {
		return CardNames.Nero;
	}

	public int getCostToPlay() {
		return 8;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Nero";
	}

	public String getDescription() {
		return "Destroys any face-up opposing building card. " +
				"The destroyed card and Nero are both discarded.";
	}

	public int getDefense() {
		// TODO Auto-generated method stub
		return 9;
	}

	public EffectTrigger getEffectTrigger() {
		// TODO Auto-generated method stub
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g) {
		return false;
		// TODO Auto-generated method stub

	}

}
