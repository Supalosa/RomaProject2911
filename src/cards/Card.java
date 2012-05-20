package cards;

import java.util.*;

import cards.activators.*;
import roma.*;
import enums.*;
import modifiers.*;


public abstract class Card implements IModifiable, Cloneable {
	
	private int ownerId; // possibly redundant, id of player 0..1 of owner
	private boolean isInPlay; // possibly redundant
	private boolean isFaceUp;
	List<IModifier> modifiers; // list of modifiers applying to this card
	List<IModifier> castedModifiers; // list of modifiers casted by this card
	private int realDefense; // defense after modifiers, etc
		
/*	private CardNames id;
	private int costToPlay;
	private int diceToActivate;
	private boolean isBuilding;
	private String name;
	private String description;
	private int defense;
	private EffectTrigger effectTrigger;*/
	
	
	public Card() {
		ownerId = -1;
		isInPlay = false;
		isFaceUp = false;
		
		modifiers = new ArrayList<IModifier>();
		castedModifiers = new ArrayList<IModifier>();
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
	 */
	public void onEnterField(Field f, int ownerId, int position) {

	}
	
	/**
	 * onLeaveField: fires when the card leaves the field (ALL cards recieve event).
	 * @param g TODO
	 */
	public boolean onLeaveField(GameVisor g, Field f, int ownerId, int position) {
		if (f.getCard(ownerId, position) == this) { // if this card is the one that left
			for (IModifier mod : modifiers) {
				mod.unapply();
			}
			modifiers.clear();
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
		boolean hasGrimReaperAura = hasModifier(GrimReaperAura.NAME);
		if (battleDie >= getRealDefense()) {
			
			/*for (int i = 0; i < Game.FIELD_SIZE; i++) {
				
				System.out.println (g.getField().getCard(ownerId, i));
				
			}
			System.out.println (getName() + " [" + getOwnerID() + "] at " + (pos) + " died: " + battleDie + " > " + getRealDefense());
			*/
			g.getField().setCard(getOwnerID(), pos, null);

			// grim reaper check
			if (hasGrimReaperAura) {
				//System.out.println ("Saved by Grim Reaper");
				g.getPlayer(ownerId).addCard(this);
			} else {
				//System.out.println ("Killed...");

				g.discard(this);
			}
			killed = true;
			
			/*for (int i = 0; i < Game.FIELD_SIZE; i++) {
				
				System.out.println (g.getField().getCard(ownerId, i));
				
			}*/
		}
		
		return killed;
		
	}
	
	
 	
	/* Modifier */
	public void castModifier (IModifiable target, IModifier mod) {
		castedModifiers.add(mod);
		mod.setCaster(this);
		target.addModifier(mod);
	}
	
	public void addModifier (IModifier mod) {
		modifiers.add(mod);
		mod.setTarget(this);
		mod.apply();
	}
	
	public void removeModifier (IModifier mod) {
		modifiers.remove(mod);
		mod.unapply();
	}
	
	public List<IModifier> getModifiers() {
		return modifiers;
	}
	
	public ModifierTarget getModifiableType() {
		return ModifierTarget.Card;
	}
	
	public boolean hasModifier (String modName) {
		boolean hasMod = false;
		
		for (IModifier mod : getModifiers()) {
			if (mod.getName().equals(modName)) {
				hasMod = true;
			}
		}
		
		return hasMod;
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
	
	public Card getCopy() {
		Card copy = null;
		try {
			copy = (Card) this.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		return copy;
	}

	
}


