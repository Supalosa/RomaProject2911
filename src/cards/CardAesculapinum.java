package cards;

import cards.activators.*;
import roma.GameVisor;
import enums.CardNames;

public class CardAesculapinum extends Card {
	
	public CardNames getID() {
	
		return CardNames.Aesculapinum;
	
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
	
		return "Aesculapinum";
	
	}

	public String getDescription() {
	
		return "The temple of Asculapius (the God of healing) enables the player to pick" +
				" up any character card from the discard pile and add it to their hand.";
	
	}

	public int getDefense() {
	
		return 2;
	
	}

	public CardParams getParams () {
	
		return new AesculapinumParams ();
	
	}

	
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		
		AesculapinumParams myParams = (AesculapinumParams) a;
		boolean performed = true;
		
		// Find the first instance of the requested card!
		int index = g.getDiscardPile().findCard(myParams.getPickedUpCardName());
		
		if (index == -1) { // time paradox because the card doesn't exist
			
			g.getController().showMessage("Oh dear! You caused a Time Paradox through Aesculapinum!");
			g.getController().showMessage("You were supposed to get a " + myParams.getPickedUpCardName()
											+ " that's no longer in the discard pile.");
			
			g.onTimeParadox();
		
		} else {
			
			Card revivedCard = g.getDiscardPile().getIndex(index);
			g.getDiscardPile().removeCard(revivedCard);			
			g.getCurrentPlayer().addCard(revivedCard);
		
		}
		
		return performed;

	}

}

