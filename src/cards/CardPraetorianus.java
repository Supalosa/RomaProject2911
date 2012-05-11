package cards;

import roma.GameVisor;
import enums.CardNames;

public class CardPraetorianus extends Card {

	public CardNames getID() {
		return CardNames.Praetorianus;
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
		return "Praetorianus";
	}

	public String getDescription() {
		return "Any of the opponent's dice disc can be blocked for one go.";
	}

	public int getDefense() {
		return 4;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos) {
		return false;
		// TODO Auto-generated method stub

	}

}
