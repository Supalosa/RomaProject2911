package roma;

import java.util.*;

import modifiers.IModifiable;
import modifiers.IModifier;
import modifiers.ModifierTarget;

import cards.*;

public class Field implements IModifiable {
	
	private Card[][] fieldData;
	List<IModifier> modifiers;
	List<String> block;
	
	public Field() {
		fieldData = new Card[Game.MAX_PLAYERS][Game.FIELD_SIZE];
		modifiers = new ArrayList<IModifier>();
		block = new ArrayList<String>();
	}
	
	/**
	 * Sets a card in the specified position.
	 * @param player Which player
	 * @param position Which dice disc (0..max) to place it next to
	 * @param c the card
	 * @return The card that was replaced, if applicable
	 */
	public Card setCard (int player, int position, Card c) {
		
		Card replacedCard = fieldData[player][position];	
		
		if (replacedCard != null) {
			replacedCard.setOwnerId(Player.NO_OWNER);
			/* hook the event for all the other cards */
			for (Card otherCards : getAllCards()) {
				otherCards.onLeaveField(this, player, position);
			}
		}
		
		fieldData[player][position] = c;
		
		if (c != null) {
			c.setOwnerId(player);
			/* hook the event for all the other cards */
			for (Card otherCards : getAllCards()) {
				otherCards.onEnterField(this, player, position);
			}
		}

		
		return replacedCard;
	}
	
	/**
	 * Remove the card from the field, regardless of its position
	 * @param c
	 */
	public void removeCard (Card c) {
		assert (c != null);
		for (int player = 0; player < Game.MAX_PLAYERS; player++) {
			for (int cardPos = 0; cardPos < Game.FIELD_SIZE; cardPos ++) {
				if (fieldData[player][cardPos] == c) {
					if (c != null) {
						c.setOwnerId(Player.NO_OWNER);
						/* hook the event for all the other cards */
						for (Card otherCards : getAllCards()) {
							otherCards.onLeaveField(this, player, cardPos);
						}
					}
					fieldData[player][cardPos] = null;
				}
			}
		}
	}
	
	public Card getCard (int player, int position) {
		return fieldData[player][position];
	}
	
	public List<Card> getSideAsList (int player) {
		List<Card> side = new ArrayList<Card>();
		for (Card cardOnSide : fieldData[player]) {
			if (cardOnSide != null) {
				side.add(cardOnSide);
			}
		}
		return side;
	}
	
	public List<Card> getAllCards () {
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0 ; i < Game.MAX_PLAYERS; i++) {
			for (Card card : fieldData[i]) {
				if (card != null) {
					cards.add(card);
				}
			}
		}
		return cards;	
	}

	@Override
	public void addModifier(IModifier mod) {
		modifiers.add(mod);
		mod.setTarget(this);
		mod.apply();
	}

	@Override
	public void removeModifier(IModifier mod) {
		modifiers.remove(mod);
		mod.unapply();
	}

	@Override
	public List<IModifier> getModifiers() {
		return modifiers;
	}
	
	@Override
	public ModifierTarget getModifiableType() {
		return ModifierTarget.Field;
	}
	
	public void setBlock(String s) {
	
		block.add(s);
	
	}
	
	public void removeBlock(String s) {
		
		block.remove(s);
		
	}
	
	public List<String> getBlocks() {
		
		return block;
		
	}

}
