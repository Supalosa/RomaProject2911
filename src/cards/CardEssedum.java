package cards;

import java.util.ArrayList;
import java.util.List;

import cards.activators.CardParams;
import cards.activators.EssedumParams;

import modifiers.*;
import roma.*;
import enums.*;

public class CardEssedum extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Essedum;
	}

	@Override
	public int getCostToPlay() {
		return 6;
	}

	@Override
	public int getDiceToActivate() {
		return 1;
	}

	@Override
	public boolean isBuilding() {
		return false;
	}

	@Override
	public String getName() {
		return "Essedum";
	}

	@Override
	public String getDescription() {
		return "The defence value of the opponent's face-up cards is reduced by 2.";
	}

	@Override
	public int getDefense() {
		return 3;
	}

	/**
	 * 
	 * When THIS card leaves the field, remove all modifiers casted by us.
	 */
	@Override
	public boolean onLeaveField(GameVisor g, Field f, int ownerId, int position) {
		int enemyId = (ownerId + 1) % Game.MAX_PLAYERS;
		Card c = f.getCard(ownerId, position);
		
		if (c == this) { // unapply all
			// need this because you cannot iterate over modifier while removing
			List<IModifier> modsToRemove = new ArrayList<IModifier>();
			for (Card myCard : f.getSideAsList(enemyId)) {

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
	 * On end of turn, remove all modifiers casted by us.
	 */
	@Override
	public void onTurnEnd(GameVisor gv, int playerId) {
		int enemyId = (playerId + 1) % Game.MAX_PLAYERS;

		
		// need this because you cannot iterate over modifier while removing
		List<IModifier> modsToRemove = new ArrayList<IModifier>();
		for (Card myCard : gv.getField().getSideAsList(enemyId)) {

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

		super.onTurnEnd(gv, playerId);
	}

	@Override
	public CardParams getParams() {
		return new EssedumParams();
	}

	/* Put the effect on all enemy cards */
	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		int enemySide = (getOwnerID() + 1) % Game.MAX_PLAYERS;

		for (Card myCard : g.getField().getSideAsList(enemySide)) {
			if (myCard != this) {
				IModifier essedumAura = new EssedumAura();
				castModifier(myCard, essedumAura);
			}
		} 
		return true;
	}
	
	
}
