package cards;

import java.util.*;

import modifiers.IModifier;

import cards.activators.CardParams;

import roma.*;
import enums.*;

/**
 * Kat is a special card, it has 9 lives.
 * @author Supalosa
 *
 */
public class CardKat extends Card {

	private int lives;
	
	public CardKat() {
		
		lives = 9;
		
	}
	public CardNames getID() {
		return CardNames.Kat;
	}

	public int getCostToPlay() {
		return 5;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Kat";
	}

	public String getDescription() {
		return "Mysterious and revered animal.  Its haunting cry lifts the" + 
				"heart of all who hear it.  Has nine lives.";
	}

	public int getDefense() {
		return 1;
	}

	public boolean performEffect(GameVisor g, int pos) {	
		
		g.getController().showMessage("Miaow");
		return true;

	}

	/**
	 * Not activatable
	 */
	@Override
	public CardParams getParams() {
		return null;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		return false;
	}
	
	/**
	 * Reset lives when the card leaves the field.
	 */
	// onLeaveField: fires whenever a card enters the field (ALL cards receive event)
	@Override
	public boolean onLeaveField(GameVisor g, Field f, int ownerId, int position) {
		
		Card enteredCard = f.getCard(ownerId, position);
		if (enteredCard == this) {
			System.out.println ("Reset life of kat");
			//lives = 9;
		}
		
		return super.onLeaveField(g, f, ownerId, position);
	}


	/**
	 * Triggers when this card is attacked. Overridable.
	 * @param c The card that is attacking this card.
	 * @param battleDie The battle die value (after modification)
	 * @return Whether the card dies or not.
	 */
	public boolean onAttacked(GameVisor g, Card c, int pos, int battleDie) {
		if (battleDie >= getRealDefense()) {
			System.out.println ("Kat [" + this.hashCode() + "] was attacked with " + lives + " lives left");
			lives--;
		}
		
		if (lives <= 0) {
			lives = 9;
			return super.onAttacked(g, c, pos, battleDie);
		} else {
			return false;
		}

	}
	
	public void setLives (int lives) {
		
		this.lives = lives;
		
	}
	
	public int getLives() {
		
		return this.lives;
		
	}
	
	/**
	 * Get a copy of this Kat.
	 * Set its lives appropriately
	 */
	@Override
	public Card getCopy() {
		CardKat copy = (CardKat) super.getCopy();
		
		copy.setLives(lives);
		System.out.println ("Kat copy [" + copy.hashCode() + "] has " + copy.getLives() + " lives");
		return copy;
	}

}
