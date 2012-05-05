package cards;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardLegionarius extends Card {

	public CardNames getID() {
		return CardNames.Legionarius;
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
		return "Legionarius";
	}

	public String getDescription() {
		return "Attacks the opponent's card which is directly opposite, " +
		"whether it is a character or a building card.";
	}

	public int getDefense() {
		return 5;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g) {
		System.out.println ("Legionarius - activated");
		return false;
	}

}
