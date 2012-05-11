package cards;

import enums.CardNames;

import roma.*;

public class CardMachina extends Card {
	

	public CardNames getID() {
		return CardNames.Machina;
	}

	public int getCostToPlay() {
		return 4;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Machina";
	}

	public String getDescription() {
		return "The player picks up their building cards and lays them " +
				"again on any dice discs. Character cards can be covered.";
	}

	public int getDefense() {
		return 4;
	}


	public boolean performEffect(GameVisor g, int pos) {
		return false;
		// TODO Auto-generated method stub

	}

}
