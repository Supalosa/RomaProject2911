package cards;

import java.util.ArrayList;
import java.util.List;

import cards.activators.CardParams;
import cards.activators.ScaenicusParams;

import roma.*;
import enums.*;

public class CardScaenicus extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Scaenicus;
	}

	@Override
	public int getCostToPlay() {
		return 8;
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
		return "Scaenicus";
	}

	@Override
	public String getDescription() {
		return "He performs no action of his own but can copy the action of any " +
				"of the player's own face-up character cards, and the next time round" +
				" that of another.";
	}

	@Override
	public int getDefense() {
		return 3;
	}


	@Override
	public CardParams getParams() {
		return new ScaenicusParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		boolean performed = true;
		ScaenicusParams myParams = (ScaenicusParams)a;
		
		/* Determine which card Scaenicus copied */
		
		// Time Paradox occurs if 'copied card' and card that's actually there is inconsistent.
		CardTypes library = new CardTypes();
		
		Card inPlace = g.getField().getCard(g.whoseTurn(), myParams.getPositionToCopy());
		
		if (inPlace == null || inPlace.getID() != myParams.getCopiedCard()) {
			
			g.getController().showMessage("Oh dear! You caused a time paradox by changing the card activated by Scaenicus.");
			g.getController().showMessage("In place: " + inPlace.getID() + ", Expected: " + myParams.getCopiedCard());
			g.onTimeParadox();
		} else {
			
			Card invokedCard = library.getCard(myParams.getCopiedCard());

			if (invokedCard != null && myParams.getCopiedParams() != null) {
			
				invokedCard.performEffect(g, pos, myParams.getCopiedParams());
				
			} else { // error...
				
				System.err.println ("SCAENICUS error in activation... invalid invokedCard or no params");
				System.exit(1);
			}
		}

		
		return performed;
	}

}
