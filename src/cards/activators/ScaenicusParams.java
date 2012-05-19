package cards.activators;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import framework.interfaces.activators.CardActivator;
import roma.GameVisor;

public class ScaenicusParams extends CardParams {

	private int battleDie;
	private int positionToCopy;
	private boolean valid;
	private CardActivator copiedActivator;
	
	public ScaenicusParams() {
		valid = true;
	}
	
	public int getBattleDie() {
		return battleDie;
	}
	
	public void setBattleDie(int dieValue) {
		battleDie = dieValue;
	}

	public int getPositionToCopy() {
		return positionToCopy;
	}
	
	public void setPositionToCopy(int pos) {
		this.positionToCopy = pos;
	}
	
	@Override
	public void query(GameVisor g, int pos) {
		valid = false;
		List<Card> characters = new ArrayList<Card>();
		for (Card c : g.getField().getSideAsList(g.whoseTurn())) {
			if (!c.isBuilding()) {
				characters.add(c);
			}
		}
		
		Card selectedCard = null;
		selectedCard = g.getController().getCard(characters, "Select a character card to lay.");

		if (selectedCard != null) {

			valid = true;
			setPositionToCopy(g.getField().findCardPosition(selectedCard));
			
		}
		
	}

	@Override
	public boolean isValid() {
		return valid;
	}

	/* these are used purely for acceptance testing.. fuck */
	
	public CardActivator getCopiedParams() {
		return copiedActivator;
	}

	public void setCopiedParams(CardActivator copiedActivator) {
		this.copiedActivator = copiedActivator;
	}


}
