package cards.activators;

import java.util.*;
import cards.*;
import enums.*;
import roma.*;

public class ScaenicusParams extends CardParams {

	private int positionToCopy;
	private boolean valid;
	private CardNames copiedCard;
	private CardParams copiedParams;

	public ScaenicusParams() {
		valid = true;
	}

	/**
	 * Gets the field position of the card that is to be copied.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @return value of positionToCopy
	 */
	public int getPositionToCopy() {
		return positionToCopy;
	}

	/**
	 * Sets the field position of the card that is to be copied.
	 * 
	 * If the card at that position is inconsistent with the parameters provided
	 * with copiedParams, a time paradox occurs.
	 * 
	 * Field position ranges from 0..Game.FIELD_SIZE
	 * 
	 * @param pos
	 */
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
		selectedCard = g.getController().getCard(characters,
				"Select a character card to copy.");

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

	/**
	 * Gets the CardNames entry of the card that is to be copied.
	 * 
	 * Is used for cross-checking when a Time Paradox occurs.
	 * 
	 * @return value of copiedCard
	 */
	public CardNames getCopiedCard() {
		return copiedCard;
	}
	
	/**
	 * Sets the CardNames entry of the card that is to be copied.
	 * 
	 * Is used for cross-checking when a Time Paradox occurs.
	 * 
	 * @param copiedCard ID of the card to be copied.
	 */
	public void setCopiedCard(CardNames copiedCard) {
		this.copiedCard = copiedCard;
	}

	/**
	 * Gets the Params object that will be fed to the copied card.
	 * 
	 * An invalid Params type will cause a Time Paradox.
	 * 
	 * @return value of copiedParams
	 */
	public CardParams getCopiedParams() {
		return copiedParams;
	}

	/**
	 * Sets the Params object that will be fed to the copied card.
	 * 
	 * An invalid Params type will cause a Time Paradox.
	 * 
	 * @return value of copiedParams
	 */
	public void setCopiedParams(CardParams copiedActivator) {
		this.copiedParams = copiedActivator;
	}

}
