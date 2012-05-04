package cards;

import roma.Game;
import enums.CardNames;
import enums.EffectTrigger;

public class CardLegat extends Card {

	public CardNames getID() {
		return CardNames.Legat;
	}

	public int getCostToPlay() {
		return 5;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Legat";
	}

	public String getDescription() {
		return "A player gets 1 victory point from the stockpile " +
				"for every dice disc not occupied by the opponent.";
	}

	public int getDefense() {
		return 2;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(Game g) {
		return false;
		// TODO Auto-generated method stub

	}

}
