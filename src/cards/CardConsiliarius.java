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
		boolean performed = true;
				
		// Time Paradox if there are not enough mappings made.
		int numCharacters = 0;
		int movements = 0;
		
		for (Card c : g.getField().getSideAsList(g.whoseTurn())) {
			
			if (!c.isBuilding()) {
				
				numCharacters ++;
				
			}
			
		}
		
		// Maintain a copy of the field, because swapping cards will cause problems
		Card[] fieldCopy = g.getField().getSide(g.whoseTurn());
		
		
		// Use a copy of the params, because getNextPosition destroys the value (cannot replay.. bad)
		List<PositionMapping> mappings = myParams.getMappings();
		
		for (PositionMapping thisMapping : mappings)  {

			Card theCard = fieldCopy[thisMapping.getInitialPos()];
			
			//Remove card from the field
			g.getField().removeCard(theCard);
			
			// Re-Add the card to the field
			g.getField().setCard(g.whoseTurn(), thisMapping.getFinalPos(), theCard);
			
			movements ++;
			
		}
		
		
		// TIME PARADOX!
		if (movements != numCharacters) {
			
			g.getController().showMessage("Consiliarius caused a Time Paradox. There were" +
										" more Character Cards than expected in the past!");
			g.onTimeParadox();
			
		}
		
		return performed;
	
	}

}
