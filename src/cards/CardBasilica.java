package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardBasilica extends Card {
	
	public CardNames getID() {
		return CardNames.Basilica;
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
		return "Basilica";
	}

	public String getDescription() {
		return "If a Forum is acti- vated (it must lie directly next to the basilica), the" +
				" player gets 2 more victory points. The Basilica itself is not activia- ted.";
	}

	public int getDefense() {
		return 5;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnPlay;
	}

	public boolean performEffect(GameVisor g) {
		g.getController().showMessage("You cannot activate this card");
		return false;
	}

}