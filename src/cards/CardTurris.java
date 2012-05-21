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
	public void onEnterField(GameVisor g, Field f, int ownerId, int position) {
		Card c = f.getCard(ownerId, position);

		if (c == this) { // apply to all the cards on our side of the field, retroactively
			System.out.println("Turris entered the field, applying to cards...");
			for (Card myCard : f.getSideAsList(ownerId)) {
				if (myCard != this) {
					// do NOT apply if it already has this modifier FROM US!
					boolean hasOurModifier = false;
					
					List<IModifier> mods = g.getModifiersOn(myCard);
					for (IModifier mod : mods) {
						System.out.println (" > Target has mod : " + mod.getName());
						if (mod.getCasterOwnerId() == ownerId && mod.getCasterPos() == position) {
							System.out.println (" > TURRIS did not apply, already has a modifier");
							hasOurModifier = true; // :( already applied (possibly due to copying game state)
						}
						
					}
					
					
					if (!hasOurModifier) {
						System.out.println(" > Applied to " + myCard);
						IModifier turrisAura = new TurrisAura();
						castModifier(g, myCard, turrisAura);
					}
				}
			}
		} else if (ownerId == this.getOwnerID()) { // else a friendly card came in

			IModifier turrisAura = new TurrisAura();
			castModifier(g, c, turrisAura);
			
		}
		super.onEnterField(g, f, ownerId, position);
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
			List<IModifier> modsToRemove = g.getModifiersBy(this);
			
			System.out.println ("Turris, left the field.");
			for (IModifier mod : modsToRemove) {
				Card modTarget = f.getCard(mod.getTargetOwnerId(), mod.getTargetPos());
				//System.out.println("Turris leaving field, applying on " + mod.getTargetOwnerId() + ", " + mod.getTargetPos());
				if (modTarget != null) {
					mod.unapply(modTarget);
					System.out.println(" > Unapplied on " + modTarget + " (@ " + mod.getTargetPos() + ")");
				} else {
				//	System.out.println ("Turris: target at " + mod.getTargetOwnerId() + ", " + mod.getTargetPos() + " no longer exists!");
				}
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
