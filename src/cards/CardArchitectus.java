package cards;

import java.util.*;

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

		
		for (Map.Entry<Card, Integer> mappings : myParams.getPositions().entrySet()) {
			Card theCard = mappings.getKey();
			// Remove card from their hand
			g.getPlayer(g.whoseTurn()).removeCard(theCard);
			
			// Add the card to the field, discarding replaced card if necessary
			if ((laidOverCard = g.getField().setCard(g.whoseTurn(), mappings.getValue()-1, theCard)) != null) {
				g.discard(laidOverCard);
			}
		}
		
		for (int i = 0; i < Game.FIELD_SIZE; i++) {
		
			System.out.println (g.getField().getCard(g.whoseTurn(), i));
		
		}

		
		return performed;

	}

	@Override
	public CardParams getParams() {
		return new ArchitectusParams();
	}

}
