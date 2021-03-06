package cards;

import cards.activators.CardParams;
import roma.GameVisor;
import enums.CardNames;

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

	public boolean performEffect(GameVisor g, int pos) {
	
		g.getController().showMessage("You cannot activate this card");
		return false;
	
	}

	/**
	 * Templum cannot be activated
	 */
	@Override
	public CardParams getParams() {
	
		return null;
	
	}

	/**
	 * Templum does nothing
	 */
	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
	
		return false;
	
	}

}
