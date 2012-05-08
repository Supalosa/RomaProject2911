package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardMercatus extends Card {
	
	public CardNames getID() {
		return CardNames.Mercatus;
	}

	public int getCostToPlay() {
		return 6;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Mercatus";
	}

	public String getDescription() {
		return "The player gets 1 victory point for every" +
				" face-up Forum that the opponent has.";
	}

	public int getDefense() {
		return 3;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g, int pos) {
		return false;
		// TODO Auto-generated method stub

	}

}
