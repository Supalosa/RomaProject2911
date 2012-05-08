package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardTemplum extends Card {
	
	public CardNames getID() {
		return CardNames.Templum;
	}

	public int getCostToPlay() {
		return 2;
	}

	public int getDiceToActivate() {
		return 0;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Templum";
	}

	public String getDescription() {
		return "If a Forum is activated (it must lie directly next to the Templum), the " +
				"third action die can be used to determi- ne the number of additional victory" +
				" points which the player gets from the general stockpile. The action dice must " +
				"not yet have been used in this go. The Templum itself is not activated separately.";
	}

	public int getDefense() {
		return 2;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnPlay;
	}

	public boolean performEffect(GameVisor g, int pos) {
		g.getController().showMessage("You cannot activate this card");
		return false;
	}

}
