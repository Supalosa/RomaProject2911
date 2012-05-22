package cards;

import java.util.*;

import cards.activators.*;
import roma.*;
import enums.*;
import modifiers.*;


public abstract class Card implements Cloneable {
	
	private int ownerId; // possibly redundant, id of player 0..1 of owner
	private boolean isInPlay; // possibly redundant
	private boolean isFaceUp;
	private int realDefense; // defense after modifiers, etc
	
	public Card() {
		
		ownerId = -1;
		isInPlay = false;
		isFaceUp = false;

		realDefense = getDefense();
		
	}
	
	/* Card Definition boilerplates */
	public String toString() {
		
		return getName();
	
	}
	
	public int getOwnerID () {
	
		return ownerId;
	
	}
	
	public void setOwnerId (int playerId) {
	
		ownerId = playerId;
	
	}
	
	public boolean isInPlay () {
	
		return isInPlay;
	
	}
	
	public boolean isFaceUp () {
	
		return isFaceUp;
	
	}
	
	/* Game Functions */
	public int getRealDefense() {
	
		return realDefense;
	
	}
	
	public void setRealDefense(int defense) {
	
		realDefense = defense;
	
	}
	/* Events */
	
	/**
	 *   onEnterField: fires whenever a card enters the field (ALL cards receive event)
	 * @param g TODO
	 */
	public void onEnterField(GameVisor g, Field f, int ownerId, int position) {

	}
	
	/**
	 * onLeaveField: fires when the card leaves the field (ALL cards recieve event).
	 * @param g TODO
	 */
	public boolean onLeaveField(GameVisor g, Field f, int ownerId, int position) {
		
		if (f.getCard(ownerId, position) == this) { // if this card is the one that left
			
			// remove all modifiers on this card
			List<IModifier> modsToRemove = g.getModifiersOn(this);
			
			for (IModifier mod : modsToRemove) {
				mod.unapply(this);
			}
		
		}
		
		return true;
	}
	
	// onTurnStart: when the turn starts
	public void onTurnStart(GameVisor gv, int playerId) {
		
	}
	
	// onTurnEnd
	public void onTurnEnd(GameVisor gv, int playerId) {
		
	}
	
	// onEnterHand: fires when the card enters the hand
	public void onEnterHand(Player p) {
		
	}
	
	// onLeaveHand: fires when this card leaves the hand
	public void onLeaveHand(Player p) {
		
	}
	
	// onEnterDeck: fires when the card enters deck
	public void onEnterDeck() {
		
	}
	
	// onLeaveDeck: fires when the card leaves the deck
	public void onLeaveDeck() {
		
	}
	
	/**
	 * onEnterDiscard: fires when this card goes to the discard pile.
	 * @return whether the card should actually go there or not.
	 */
	public boolean onEnterDiscard() {
		
		boolean remove = true;

		return remove;
	
	}
	
	// onLeaveDiscard: 
	public void onLeaveDiscard() {
		
	}
	
	// activated (before performEffect)
	public void onBeforeActivate() {
		
	}
	
	// activated (after performEffect)
	public void onAfterActivate() {
		
	}
	
	/**
	 * Triggers when this card is attacked. Overridable.
	 * @param g TODO
	 * @param c The card that is attacking this card.
	 * @param pos TODO
	 * @param battleDie The battle die value (after modification)
	 * @return Whether the card dies or not
	 */
	public boolean onAttacked(GameVisor g, Card c, int pos, int battleDie) {
		
		boolean killed = false;
		int ownerId = getOwnerID();
		boolean hasGrimReaperAura = hasModifier(g, GrimReaperAura.NAME);
		
		if (battleDie >= getRealDefense()) {
			
			g.getField().setCard(getOwnerID(), pos, null);

			// grim reaper check
			if (hasGrimReaperAura) {

				g.getPlayer(ownerId).addCard(this);
			
			} else {

				g.discard(this);

			}
			
			killed = true;
			
		}
		
		return killed;
		
	}
	
	
	public abstract CardNames getID();
	public abstract int getCostToPlay();
	public abstract int getDiceToActivate();
	public abstract boolean isBuilding();
	public abstract String getName();
	public abstract String getDescription();
	public abstract int getDefense();

	public abstract CardParams getParams();	
	public abstract boolean performEffect (GameVisor g, int pos, CardParams a);
	
	/**
	 * Returns an immutable copy of this card (deep copy)
	 * @return
	 */
	public Card getCopy() {
		
		Card copy = null;
		
		try {
		
			copy = (Card) this.clone();
			
			// Cards can only get realDefense from auras, which is recalculated...
			// So need to reset def when cloned
			copy.setRealDefense(copy.getDefense());	
			
		} catch (CloneNotSupportedException e) {
			
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		
		}
		
		return copy;
	
	}
	
	/**
	 * Modifier stuff
	 */
	
	/**
	 * Casts a modifier onto the specified card
	 * @param c
	 * @param modifier
	 */
	public void castModifier(GameVisor g, Card c, IModifier modifier) {
		
		int myOwner = g.getField().findCardOwner(this);
		int myPos = g.getField().findCardPosition(this);
		
		int targetOwner = g.getField().findCardOwner(c);
		int targetPos = g.getField().findCardPosition(c);
		
		modifier.setCaster(myOwner, myPos);
		modifier.setTarget(targetOwner, targetPos);
	
		g.addModifier(modifier);
		
		modifier.apply(c);
	
	}

	/**
	 * Returns whether this card has a modifier on it
	 * @param g
	 * @param modifierName
	 * @return
	 */
	public boolean hasModifier(GameVisor g, String modifierName) {
		
		boolean result = false;
		
		List<IModifier> mods = g.getModifiersOn(this);
		
		for (IModifier mod : mods) {
			
			if (mod.getName().equals(modifierName)) {
			
				result = true;
			
			}
			
		}
		
		return result;
	
	}
	
}


