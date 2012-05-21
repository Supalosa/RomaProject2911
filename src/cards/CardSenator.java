package cards;

import java.util.*;

import cards.activators.*;

import roma.*;
import enums.CardNames;

public class CardSenator extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Senator;
	}

	@Override
	public int getCostToPlay() {
		return 3;
	}

	@Override
	public int getDiceToActivate() {
		return 1;
	}

	@Override
	public boolean isBuilding() {
		return false;
	}

	@Override
	public String getName() {
		return "Senator";
	}

	@Override
	public String getDescription() {
		return "enables the player to lay as many character cards as they wish free of charge. " +
				"The player is allowed to cover any cards.";
	}

	@Override
	public int getDefense() {
		return 3;
	}

	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		SenatorParams myParams = (SenatorParams)a;
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
		
		/*for (int i = 0; i < Game.FIELD_SIZE; i++) {
		
			System.out.println (g.getField().getCard(g.whoseTurn(), i));
		
		}*/

		
		return performed;

	}

	@Override
	public CardParams getParams() {
		return new SenatorParams();
	}

}
