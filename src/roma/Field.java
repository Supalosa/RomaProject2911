package roma;

import java.util.*;

import modifiers.*;

import cards.*;

public class Field implements Cloneable {

	// TODO: make FieldPosition a class
	private Card[][] fieldData;
	private List<IModifier> modifiers;
	private boolean[][] fieldBlocked;
	private Game game;

	public Field(Game g) {

		fieldData = new Card[Game.MAX_PLAYERS][Game.FIELD_SIZE];
		modifiers = new ArrayList<IModifier>();
		fieldBlocked = new boolean[Game.MAX_PLAYERS][Game.FIELD_SIZE];
		game = g;

		for (int i = 0; i < Game.MAX_PLAYERS; i++) {

			for (int j = 0; j < Game.FIELD_SIZE; j++) {

				fieldBlocked[i][j] = false;

			}

		}

	}

	/**
	 * Sets a card in the specified position.
	 * 
	 * @param player
	 *            Which player
	 * @param position
	 *            Which dice disc (0..max) to place it next to
	 * @param c
	 *            the card
	 * @return The card that was replaced, if applicable
	 */
	public Card setCard(int player, int position, Card c) {

		assert (player >= 0 && player < fieldData.length);
		assert (position >= 0 && position < Game.FIELD_SIZE);

		Card replacedCard = fieldData[player][position];

		if (replacedCard != null) {

			removeCard(replacedCard);

		}

		fieldData[player][position] = c;

		if (c != null) {

			c.setOwnerId(player);

			for (Card otherCards : getAllCards()) {

				otherCards.onEnterField(game.getGameVisor(), this, player,
						position);

			}

		}

		return replacedCard;

	}

	/**
	 * Forces a card in the specified position. Does NOT trigger events
	 * 
	 * @param player
	 *            Which player
	 * @param position
	 *            Which dice disc (0..max) to place it next to
	 * @param c
	 *            the card
	 * @return The card that was replaced, if applicable
	 */
	public Card setCardForced(int player, int position, Card c) {

		assert (player >= 0 && player < fieldData.length);
		assert (position >= 0 && position < Game.FIELD_SIZE);

		Card replacedCard = fieldData[player][position];

		if (replacedCard != null) {

			replacedCard.setOwnerId(Player.NO_OWNER);

		}

		fieldData[player][position] = c;

		if (c != null) {

			c.setOwnerId(player);

		}

		return replacedCard;

	}

	/**
	 * Remove the card from the field, regardless of its position
	 * 
	 * @param c
	 */
	public void removeCard(Card c) {

		assert (c != null);

		for (int player = 0; player < Game.MAX_PLAYERS; player++) {

			for (int cardPos = 0; cardPos < Game.FIELD_SIZE; cardPos++) {

				if (fieldData[player][cardPos] == c) {

					if (c != null) {

						c.setOwnerId(Player.NO_OWNER);
						/* hook the event for all the other cards */

						for (Card otherCards : getAllCards()) {

							otherCards.onLeaveField(game.getGameVisor(), this,
									player, cardPos);

						}

					}

					fieldData[player][cardPos] = null;

				}

			}

		}

	}

	public Card getCard(int player, int position) {

		return fieldData[player][position];

	}

	public List<Card> getSideAsList(int player) {

		List<Card> side = new ArrayList<Card>();

		for (Card cardOnSide : fieldData[player]) {

			if (cardOnSide != null) {

				side.add(cardOnSide);

			}

		}

		return side;

	}

	public List<Card> getAllCards() {

		List<Card> cards = new ArrayList<Card>();

		for (int i = 0; i < Game.MAX_PLAYERS; i++) {

			for (Card card : fieldData[i]) {

				if (card != null) {

					cards.add(card);

				}

			}

		}

		return cards;

	}

	/**
	 * Return a copy of one side of the field. Useful because it preserves
	 * indexing.
	 */
	public Card[] getSide(int player) {

		return fieldData[player].clone();

	}

	/**
	 * Sets the specified field position as unusable.
	 */
	public void setBlock(int blockedPlayer, int blockedPosition) {

		fieldBlocked[blockedPlayer][blockedPosition] = true;

	}

	public void removeBlock(int blockedPlayer, int blockedPosition) {

		fieldBlocked[blockedPlayer][blockedPosition] = false;

	}

	public boolean isBlocked(int player, int position) {

		return (fieldBlocked[player][position] == true);

	}

	/**
	 * Find the specified card in the field (position) Disregards side
	 * 
	 * @param c
	 *            The card
	 * @return Field position of the card. -1 if not found.
	 */
	public int findCardPosition(Card c) {

		int pos = -1;

		for (int j = 0; j < Game.MAX_PLAYERS; j++) {

			for (int i = 0; i < Game.FIELD_SIZE; i++) {

				if (fieldData[j][i] == c) {

					pos = i;

				}

			}

		}

		return pos;

	}

	/**
	 * Find the owner of the specified card.
	 * 
	 * @param c
	 *            The card
	 * @return Owner of the card. -1 if not found.
	 */
	public int findCardOwner(Card c) {

		int owner = -1;

		for (int j = 0; j < Game.MAX_PLAYERS; j++) {

			for (int i = 0; i < Game.FIELD_SIZE; i++) {

				if (fieldData[j][i] == c) {

					owner = j;

				}

			}

		}

		return owner;

	}

	/**
	 * Provides the ability to clone this object. This is a deep copy, so the
	 * Cards will be updated in whatever state they will be in this game. The
	 * clone can be used as a deep clone;
	 */
	public Field clone() {

		Field clonedField = null;

		try {

			clonedField = (Field) super.clone();
			clonedField.modifiers = new ArrayList<IModifier>(
					clonedField.modifiers);
			clonedField.fieldData = clonedField.fieldData.clone();

			for (int i = 0; i < Game.MAX_PLAYERS; i++) {

				clonedField.fieldData[i] = clonedField.fieldData[i].clone();

			}

			for (int i = 0; i < Game.MAX_PLAYERS; i++) {

				for (int j = 0; j < Game.FIELD_SIZE; j++) {

					if (clonedField.fieldData[i][j] != null) {

						clonedField.fieldData[i][j] = clonedField.fieldData[i][j]
								.getCopy();

					}

				}

			}

		} catch (CloneNotSupportedException e) {

			System.err.println(e.getMessage());
			e.printStackTrace();

		}

		return clonedField;

	}

	/**
	 * Deletes all the cards from the fields. Since they are not going anywhere,
	 * it is assumed this is because of a wipe (i.e. Telephone Box) - dont' need
	 * to trigger events
	 */
	public void clearField() {

		for (int i = 0; i < Game.MAX_PLAYERS; i++) {

			for (int j = 0; j < Game.FIELD_SIZE; j++) {

				if (fieldData[i][j] != null) {

					fieldData[i][j] = null;

				}

			}

		}

	}

}
