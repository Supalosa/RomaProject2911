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
	 * Reset lives when the card enters the field.
	 */
	// onEnterField: fires whenever a card enters the field (ALL cards receive event)
	public void onEnterField(Field f, int ownerId, int position) {
		super.onEnterField(f, ownerId, position);
		
		lives = 9;
	}


	/**
	 * Triggers when this card is attacked. Overridable.
	 * @param c The card that is attacking this card.
	 * @param battleDie The battle die value (after modification)
	 * @return Whether the card dies or not.
	 */
	public boolean onAttacked(GameVisor g, Card c, int pos, int battleDie) {
		if (battleDie >= getRealDefense()) {
			System.out.println ("Kat was attacked with " + lives + " lives left");
			lives--;
		}
		
		// the way this is structured is that onAttacked (Card) will only be called if
		// lives <= 0
		return (lives <= 0 && super.onAttacked(g, c, pos, battleDie));

	}

}
