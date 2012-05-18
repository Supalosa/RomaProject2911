package cards;

import java.util.*;
import cards.activators.*;
import roma.*;
import enums.CardNames;

public class CardConsiliarius extends Card {

	public CardNames getID() {
		return CardNames.Consiliarius;
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
		return "Consiliarius";
	}

	public String getDescription() {
		return "The player picks up their character cards and can then lay them " +
		"again on any dice disc. Buildings can be covered.";
	}

	public int getDefense() {
		return 4;
	}

	@Override
	public CardParams getParams() {
		return new ConsiliariusParams();
	}


	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		ConsiliariusParams myParams = (ConsiliariusParams)a;
		boolean performed = false;
		
		Map<Card, Integer> realMappings = new HashMap<Card, Integer>();
		for (Map.Entry<Integer, Integer> mappings : myParams.getPositions().entrySet()) {
			Card theCard = g.getField().getCard(g.whoseTurn(), mappings.getKey());
			
			realMappings.put(theCard, mappings.getValue());
		}
		
		for (Map.Entry<Card, Integer> mappings : realMappings.entrySet()) {
			Card theCard = mappings.getKey();
			
			//Remove card from the field
			g.getField().removeCard(theCard);
			
			// Re-Add the card to the field
			g.getField().setCard(g.whoseTurn(), mappings.getValue(), theCard);
			
			//System.out.println("Card at (" + theCard.getName() + ") -> " + mappings.getValue());
		}
		
		return performed;
	}

}
