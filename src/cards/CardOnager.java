package cards;

import enums.*;

import roma.*;

public class CardOnager extends Card {
	
	public CardNames getID() {
		return CardNames.Onager;
	}

	public int getCostToPlay() {
		return 5;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Onager";
	}

	public String getDescription() {
		return "This Roman cata- pult attacks any opposing building. " +
				"The battle die is thrown once.";
	}

	public int getDefense() {
		return 4;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g, int pos) {
		return false;
		// TODO Auto-generated method stub

	}


}
