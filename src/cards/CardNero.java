package cards;

import cards.activators.CardParams;
import cards.activators.NeroParams;

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
		
		
		// RG - seems like Nero always sacrifices even if it can't kill
		g.getField().setCard(g.whoseTurn(), pos-1, null);
		g.discard(this);
		
		if (targetCard != null && targetCard.isBuilding()) {

			g.getField().removeCard(targetCard);
			g.discard(targetCard);	
			
		} else {
			
			g.getController().showMessage("Nero is not crazy enough to attack the " + 
											targetCard + ", it's not a building!");
		
		}

		return performed;
	
	}

}
