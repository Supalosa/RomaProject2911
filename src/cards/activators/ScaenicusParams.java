package cards.activators;

import java.util.ArrayList;
import java.util.List;

import cards.Card;
import enums.CardNames;
import framework.interfaces.activators.CardActivator;
import roma.GameVisor;

public class ScaenicusParams extends CardParams {

	private int battleDie;
	private int positionToCopy;
	private boolean valid;
	private CardNames copiedCard;
	private CardParams copiedParams;
	
	public ScaenicusParams() {
		valid = true;
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
		selectedCard = g.getController().getCard(characters, "Select a character card to copy.");

		if (selectedCard != null) {

			valid = true;
			setPositionToCopy(g.getField().findCardPosition(selectedCard));
			setCopiedCard(selectedCard.getID());
			
			// get the parameters for the copied card
			CardParams params = selectedCard.getParams();
			params.query(g, pos);
		}
		
	}

	@Override
	public boolean isValid() {
		return valid;
	}


	
	public CardNames getCopiedCard() {
		return copiedCard;
	}

	public void setCopiedCard(CardNames copiedCard) {
		this.copiedCard = copiedCard;
	}

	public CardParams getCopiedParams() {
		return copiedParams;
	}

	public void setCopiedParams(CardParams copiedActivator) {
		this.copiedParams = copiedActivator;
	}


}
