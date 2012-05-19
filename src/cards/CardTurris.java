package cards;

import java.util.*;

import cards.activators.CardParams;
import modifiers.*;
import enums.*;
import roma.*;

public class CardTurris extends Card {
	
	public CardNames getID() {
		return CardNames.Turris;
	}

	public int getCostToPlay() {
		return 6;
	}

	public int getDiceToActivate() {
		return 0;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Turris";
	}

	public String getDescription() {
		return "As long as the Turris is face-up, the defence value of " +
				"all the player's other face-up cards increases by 1.";
	}

	public int getDefense() {
		return 6;
	}
	
	/**
	 * When a card enters the field, apply a modifier on it if it's on our side.
	 * If that card happens to be this one, then apply the modifier to all cards
	 */
	@Override
	public void onEnterField(Field f, int ownerId, int position) {
		Card c = f.getCard(ownerId, position);

		if (c == this) { // apply to all the cards on our side of the field, retroactively
			for (Card myCard : f.getSideAsList(ownerId)) {
				if (myCard != this) {
					IModifier turrisAura = new TurrisAura();
					castModifier(myCard, turrisAura);
				}
			}
		} else if (ownerId == this.getOwnerID()) { // else a friendly card came in

			IModifier turrisAura = new TurrisAura();
			castModifier(c, turrisAura);
			
		}
		super.onEnterField(f, ownerId, position);
	}
	
	/**
	 * Ignore if another card left the field.
	 * 
	 * When THIS card leaves the field, remove all modifiers casted by us.
	 */
	@Override
	public boolean onLeaveField(GameVisor g, Field f, int ownerId, int position) {
		
		Card c = f.getCard(ownerId, position);
		
		if (c == this) { // unapply all
			// need this because you cannot iterate over modifier while removing
			List<IModifier> modsToRemove = new ArrayList<IModifier>();
			for (Card myCard : f.getSideAsList(ownerId)) {

				if (myCard != this) {
					for (IModifier modifier : myCard.getModifiers()) {
						if (modifier.getCaster() == this) {
							modsToRemove.add(modifier);
						}
					}
				}
			}
			
			for (IModifier mod : modsToRemove) {
				mod.getTarget().removeModifier(mod);
			}
		}
		
		return super.onLeaveField(g, f, ownerId, position);
	}

	/**
	 * Turris is not activatable
	 */
	@Override
	public CardParams getParams() {
		return null;
	}

	/**
	 * No effect
	 */
	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		return false;
	}
	


}
