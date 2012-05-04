package cards;

import roma.*;
import enums.*;

public class CardArchitectus extends Card {

	public CardNames getID() {
		return CardNames.Architectus;
	}

	public int getCostToPlay() {
		return 3;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Architectus";
	}

	public String getDescription() {
		return "enables the player to lay as many building cards as they " +
				"wish free of charge. The player is allowed to cover any cards.";
	}

	public int getDefense() {
		return 4;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(Game g) {
		return false;
		// TODO Auto-generated method stub

	}

}
