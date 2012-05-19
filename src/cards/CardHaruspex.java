package cards;

import java.util.ArrayList;
import java.util.List;

import cards.activators.AesculapinumParams;
import cards.activators.CardParams;
import cards.activators.HaruspexParams;

import roma.GameVisor;
import enums.CardNames;

public class CardHaruspex extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Haruspex;
	}

	@Override
	public int getCostToPlay() {
		return 4;
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
		return "Haruspex";
	}

	@Override
	public String getDescription() {
		return "The player can choose any card from the pile of face-down cards and add " +
				"it to their hand. Afterwards the pile is shuffled.";
	}

	@Override
	public int getDefense() {
		return 3;
	}

	public CardParams getParams () {
		return new HaruspexParams ();
	}

	
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		
		HaruspexParams myParams = (HaruspexParams) a;
		boolean performed = true;
		
		Card pickedCard = g.getDeck().getIndex(myParams.getPickedUpCard());
		
		g.getDeck().removeCard(pickedCard);			
		g.getCurrentPlayer().addCard(pickedCard);

		
		return performed;

	}
}
