package cards;

import roma.Game;
import enums.*;

public class CardSicarius extends Card {

	public CardNames getID() {
		return CardNames.Sicarius;
	}

	public String getName() {
		return "Sicarius";
	}
	
	public int getCostToPlay() {
		return 9;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}


	public String getDescription() {
		return "eliminates an opposing, face-up character card. " +
				"The opposing card and the Sicarius are both discarded";
	}

	public int getDefense() {
		return 2;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(Game g) {
		return false;
		// nothing
	}

}
