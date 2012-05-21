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
		
		Card revivedCard = g.getDiscardPile().getIndex(myParams.getPickedUpCard());
		
		// Time paradox if wrong card or null
		if (revivedCard == null || revivedCard.getID() != myParams.getPickedUpCardName()) {
			g.getController().showMessage("Oh dear! You caused a Time Paradox through Aesculapinum!");
			g.onTimeParadox();
		} else {
			g.getDiscardPile().removeCard(revivedCard);			
			g.getCurrentPlayer().addCard(revivedCard);
		}
		
		
		return performed;

	}


}

