package cards;

import enums.CardNames;
import enums.EffectTrigger;

import roma.*;

public class CardTurris extends Card {
	
	public CardNames getID() {
		return CardNames.Turris;
	}

	public int getCostToPlay() {
		return 6;
	}

	public int getDiceToActivate() {
		return 0;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Turris";
	}

	public String getDescription() {
		return "As long as the Turris is face-up, the defence value of " +
				"all the player's other face-up cards increases by 1.";
	}

	public int getDefense() {
		return 6;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnPlay;
	}

	public boolean performEffect(GameVisor g) {
		return false;
		// TODO Auto-generated method stub

	}

}
