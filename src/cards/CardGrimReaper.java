package cards;

import java.util.*;

import modifiers.GrimReaperAura;
import modifiers.IModifier;

import cards.activators.CardParams;

import roma.*;
import enums.*;

public class CardGrimReaper extends Card {

	public CardNames getID() {
		
		return CardNames.GrimReaper;
	
	}

	public int getCostToPlay() {
	
		return 6;
	
	}

	public int getDiceToActivate() {
	
		return 1;
	
	}

	public boolean isBuilding() {
	
		return false;
	
	}

	public String getName() {
	
		return "Grim Reaper";
	
	}

	public String getDescription() {
	
		return "Once placed on a disc this card provides a chance to cheat death." +
				"The player's other character cards are returned to the player's hand" +
				"rather than to the discard pile whenever they are successfully attacked" +
				"and defeated by the opponent.";
	
	}

	public int getDefense() {
	
		return 3;
	
	}

	
	/**
	 * When a card enters the field, apply a modifier on it if it's on our side.
	 * If that card happens to be this one, then apply the modifier to all cards
	 */
	@Override
	public void onEnterField(GameVisor g, Field f, int ownerId, int position) {
		
		Card c = f.getCard(ownerId, position);

		// Note: Characters only
		if (c == this) { // apply to all the cards on our side of the field, retroactively
			
			for (Card myCard : f.getSideAsList(ownerId)) {
			
				if (myCard != this && !myCard.isBuilding()) {
				
					IModifier grimReaperAura = new GrimReaperAura();
					castModifier(g, myCard, grimReaperAura);
				
				}
			
			}
		
		} else if (ownerId == this.getOwnerID() && !c.isBuilding()) { // else a friendly card came in

			IModifier grimReaperAura = new GrimReaperAura();
			castModifier(g, c, grimReaperAura);
			
		}
		
		super.onEnterField(g, f, ownerId, position);
	
	}
	
	/**
	 * When another card leaves the field (belonging to us), return it to the player hand
	 * 
	 * When THIS card leaves the field, remove all modifiers casted by us.
	 */
	@Override
	public boolean onLeaveField(GameVisor g, Field f, int ownerId, int position) {
		
		boolean allowLeave = true;
		Card c = f.getCard(ownerId, position);
		
		if (c == this) { // unapply all
			// Remove all modifiers casted by us
			List<IModifier> modsToRemove = g.getModifiersBy(this);
			
			for (IModifier mod : modsToRemove) {
		
				Card modTarget = f.getCard(mod.getTargetOwnerId(), mod.getTargetPos());
				mod.unapply(modTarget);
			
			}
			
		}
		
		return (allowLeave && super.onLeaveField(g, f, ownerId, position));
	
	}

	/**
	 * Grim Reaper is not activatable
	 */
	@Override
	public CardParams getParams() {

		return null;
	
	}

	/**
	 * Grim reaper has no effect
	 */
	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {

		return false;
	
	}

}
