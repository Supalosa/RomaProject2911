package cards;

import java.security.spec.MGF1ParameterSpec;
import java.util.*;

import cards.activators.CardParams;
import cards.activators.TelephoneBoxParams;

import roma.*;
import enums.*;

public class CardTelephoneBox extends Card {

	public CardNames getID() {
		return CardNames.TelephoneBox;
	}

	public int getCostToPlay() {
		return 5;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Telephone Box";
	}

	public String getDescription() {
		return "When activated by an action die the telephone Box card sends " +
				"one of the owner's cards already on the board forwards or " +
				"backwards in time.  The sent card is called the time-traveling card.";
	}

	public int getDefense() {
		return 2;
	}

	public boolean performEffect(GameVisor g, int pos) {	
		
		g.getController().showMessage("Telephone Box activated");
		return true;

	}

	@Override
	public CardParams getParams() {
		return new TelephoneBoxParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		TelephoneBoxParams myParams = (TelephoneBoxParams)a;
		
		System.out.println ("TelephoneBox: " + myParams.getDiceToSend() + ", " + myParams.isGoForward() + ", " + myParams.getDiceToUse());
		
		Card timeTravellingCard = g.getField().getCard(g.whoseTurn(), myParams.getDiceToSend()-1);
		
		if (timeTravellingCard != null) {
			
			if (myParams.isGoForward()) { // easy to go forward
				
				// Remove the card from the field
				g.getField().setCard(g.whoseTurn(), myParams.getDiceToSend()-1, null);
				
				// Make it pending
				g.addPendingFutureCard(new TimeTravellingCard(timeTravellingCard, g.getTurnNumber() + myParams.getDiceToUse(), myParams.getDiceToSend()-1, g.whoseTurn()));
				
				// Use the dice
				g.useDice(myParams.getDiceToUse());
				
			}
			
		} else {
			
			// time paradox
			
		}
		
		return true;
	}

}
