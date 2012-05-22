package cards;

import cards.activators.*;
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
		return "Enables the player to lay as many building cards as they " +
				"wish free of charge. The player is allowed to cover any cards.";
	}

	public int getDefense() {
		return 4;
	}

	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		ArchitectusParams myParams = (ArchitectusParams)a;
		boolean performed = true;
		Card laidOverCard;

		
		for (CardMapping mappings : myParams.getPositions()) {
			Card theCard = g.getCurrentPlayer().getCard(mappings.getInitialCard());
			// Remove card from their hand
			g.getCurrentPlayer().removeCard(theCard);
			
			// Add the card to the field, discarding replaced card if necessary
			if ((laidOverCard = g.getField().setCard(g.whoseTurn(), mappings.getFinalPos()-1, theCard)) != null) {
				g.discard(laidOverCard);
			}
		}

		
		return performed;

	}


	@Override
	public CardParams getParams() {
		return new ArchitectusParams();
	}

}
