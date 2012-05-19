package cards;

import java.util.*;

import cards.activators.CardParams;
import cards.activators.NeroParams;
import cards.activators.SicariusParams;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardNero extends Card {

	public CardNames getID() {
		return CardNames.Nero;
	}

	public int getCostToPlay() {
		return 8;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Nero";
	}

	public String getDescription() {
		return "Destroys any face-up opposing building card. " +
				"The destroyed card and Nero are both discarded.";
	}

	public int getDefense() {
		return 9;
	}

	@Override
	public CardParams getParams() {
		return new NeroParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		boolean performed = true;
		NeroParams myParams = (NeroParams) a;
		
		int enemyPos = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card targetCard = g.getField().getCard(enemyPos, myParams.getTargetPos());
		
		if (targetCard != null && targetCard.isBuilding()) {
			performed = true;
			g.getField().setCard(g.whoseTurn(), pos-1, null);
			g.discard(this);
			
			g.getField().removeCard(targetCard);
			g.discard(targetCard);
			
			
		} else {
			
		}

		return performed;
	}

}
