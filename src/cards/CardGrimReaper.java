package cards;

import java.util.*;

import roma.*;
import enums.*;

public class CardGrimReaper extends Card {

	public CardNames getID() {
		return CardNames.GrimReaper;
	}

	public int getCostToPlay() {
		return 6;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Grim Reaper";
	}

	public String getDescription() {
		return "Once placed on a disc this card provides a chance to cheat death." +
				"The player's other character cards are returned to the player's hand" +
				"rather than to the discard pile whenever they are successfully attacked" +
				"and defeated by the opponent.";
	}

	public int getDefense() {
		return 3;
	}

	public boolean performEffect(GameVisor g, int pos) {	
		
		g.getController().showMessage("This card cannot be activated.");
		return false;

	}

}
