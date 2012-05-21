package cards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cards.activators.CardParams;
import cards.activators.ConsiliariusParams;
import cards.activators.MachinaParams;
import cards.activators.PositionMapping;

import enums.CardNames;

import roma.*;

public class CardMachina extends Card {
	

	public CardNames getID() {
		return CardNames.Machina;
	}

	public int getCostToPlay() {
		return 4;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Machina";
	}

	public String getDescription() {
		return "The player picks up their building cards and lays them " +
				"again on any dice discs. Character cards can be covered.";
	}

	public int getDefense() {
		return 4;
	}


	@Override
	public CardParams getParams() {
		return new MachinaParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		MachinaParams myParams = (MachinaParams)a;
		boolean performed = true;
		
		// Time Paradox if there are not enough mappings made.
		int numBuildings = 0;
		int movements = 0;
		for (Card c : g.getField().getSideAsList(g.whoseTurn())) {
			
			if (c.isBuilding()) {
				
				numBuildings ++;
				
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
			
			//System.out.println ("ConsilPerform: " + thisMapping.getInitialPos() + " -> " + thisMapping.getFinalPos());
			movements ++;
			
		}
		
		
		// TIME PARADOX!
		if (movements != numBuildings) {
			g.getController().showMessage("Machina caused a Time Paradox. There were more Character Cards than expected in the past!");
			g.onTimeParadox();
			
		}
		/*for (int i = 0; i < Game.FIELD_SIZE; i++) {
			
			System.out.println (g.getField().getCard(g.whoseTurn(), i));
			
		}*/
		
		return performed;
	}

}
