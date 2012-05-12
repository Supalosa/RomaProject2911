package cards;

import java.util.*;

import roma.*;
import enums.*;

public class CardKat extends Card {

	public CardNames getID() {
		return CardNames.Kat;
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
		return "Kat";
	}

	public String getDescription() {
		return "Mysterious and revered animal.  Its haunting cry lifts the" + 
				"heart of all who hear it.  Has nine lives.";
	}

	public int getDefense() {
		return 1;
	}

	public boolean performEffect(GameVisor g, int pos) {	
		
		g.getController().showMessage("Miaow");
		return true;

	}

}
