package cards.activators;

import java.util.ArrayList;
import java.util.List;

import roma.*;
import cards.*;
import enums.*;

public class AesculapinumParams extends CardParams {

	/**
	 * The n'th card on the discard pile
	 */
	private int pickedUpCard;

	/**
	 * We also store the pickedUpCardName so we can check if the wrong card was
	 * picked up the next time.
	 */
	private CardNames pickedUpCardName;

	/**
	 * Constructs a Parameter set for the Aesculapinum.
	 */
	public AesculapinumParams() {
		pickedUpCard = -1;
	}

	/**
	 * Queries the given GameVisor to ask which card the User would like to add
	 * to their hand.
	 */
	@Override
	public void query(GameVisor g, int pos) {
		List<Card> characters = new ArrayList<Card>();

		for (Card c : g.getDiscardPile().asList()) {

			if (!c.isBuilding()) {

				characters.add(c);

			}

		}

		if (!characters.isEmpty()) {

			Card selected = g.getController().getCard(characters,
					"Pick the card you wish to add to your hand");
			// Get the position of this card in the discard pile.
			if (selected != null) {
				int discardPosition = 0;
				for (Card c : g.getDiscardPile().asList()) {
					if (c == selected) {
						setPickedUpCard(discardPosition);
						setPickedUpCardName(c.getID());
					}
					discardPosition++;
				}
			}
		} else {

			setError("There are no Character cards on the discard pile!");

		}
	}

	/**
	 * Sets the card to be picked up.
	 * 
	 * @param pickedUp
	 */
	public void setPickedUpCard(int pickedUp) {
		pickedUpCard = pickedUp;
	}

	/**
	 * Sets the CardName that was picked up. This is used to actually get the
	 * card, to ensure the same card comes out every time this Action is
	 * executed (i.e. Telephone Box having the card in a different index
	 */
	public void setPickedUpCardName(CardNames name) {

		pickedUpCardName = name;

	}

	/**
	 * Gets the card to be picked up
	 */
	public int getPickedUpCard() {
		return pickedUpCard;
	}

	/**
	 * Gets the CardName entry that was picked up. This is the parameter that is
	 * actually used in CardAesculapinum to determine which card to pull out
	 * 
	 * @return
	 */
	public CardNames getPickedUpCardName() {
		return pickedUpCardName;
	}

	/**
	 * This parameter is invalid if no card has been selected.
	 */
	public boolean isValid() {
		return (pickedUpCard != -1);
	}

}
