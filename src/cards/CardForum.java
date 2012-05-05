package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardForum extends Card {
	
	public CardNames getID() {
		return CardNames.Forum;
	}

	public int getCostToPlay() {
		return 5;
	}

	public int getDiceToActivate() {
		return 2;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Forum";
	}

	public String getDescription() {
		return "requires 2 action dice: one to activate the Forum and the other" +
				" to determine how many victory points the player receives.";
	}

	public int getDefense() {
		return 5;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g) {
		return false;
		// TODO Auto-generated method stub

	}

}
