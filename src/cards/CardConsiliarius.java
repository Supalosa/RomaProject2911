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
		
		PositionMapping thisMapping= myParams.getNextPosition();
		
		// Maintain a copy of the field, because swapping cards will cause problems
		
		Card[] fieldCopy = g.getField().getSide(g.whoseTurn());
		
		do {
			//Card theCard = g.getField().getCard(g.whoseTurn(), thisMapping.getInitialPos());
			Card theCard = fieldCopy[thisMapping.getInitialPos()];
			
			//Remove card from the field
			g.getField().removeCard(theCard);
			
			// Re-Add the card to the field
			g.getField().setCard(g.whoseTurn(), thisMapping.getFinalPos(), theCard);
			
			//System.out.println ("ConsilPerform: " + thisMapping.getInitialPos() + " -> " + thisMapping.getFinalPos());
			
		} while ((thisMapping = myParams.getNextPosition()) != null);
		
		/*for (int i = 0; i < Game.FIELD_SIZE; i++) {
			
			System.out.println (g.getField().getCard(g.whoseTurn(), i));
			
		}*/
		
		return performed;
	}

}
