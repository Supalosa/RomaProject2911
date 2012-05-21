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

		Card c = f.getCard(ownerId, position);
		
		if (c == this) { // unapply all
			// Remove all modifiers casted by us
			List<IModifier> modsToRemove = g.getModifiersBy(this);
			
			for (IModifier mod : modsToRemove) {
				Card modTarget = f.getCard(mod.getTargetOwnerId(), mod.getTargetPos());
				mod.unapply(modTarget);
			}

		}
		
		return super.onLeaveField(g, f, ownerId, position);
	}
	
	/**
	 * On end of turn, remove all modifiers casted by us.
	 */
	@Override
	public void onTurnEnd(GameVisor gv, int playerId) {

		// Remove all modifiers casted by us
		List<IModifier> modsToRemove = gv.getModifiersBy(this);
		
		for (IModifier mod : modsToRemove) {
			Card modTarget = gv.getField().getCard(mod.getTargetOwnerId(), mod.getTargetPos());
			mod.unapply(modTarget);
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
				castModifier(g, myCard, essedumAura);
			}
		} 
		return true;
	}
	
	
}
