package cards;

import cards.activators.CardParams;
import cards.activators.PraetorianusParams;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardPraetorianus extends Card {
	
	public CardNames getID() {
	
		return CardNames.Praetorianus;
	
	}

	public int getCostToPlay() {
	
		return 4;
	
	}

	public int getDiceToActivate() {
	
		return 1;
	
	}

	public boolean isBuilding() {
	
		return false;
	
	}

	public String getName() {
	
		return "Praetorianus";
	
	}

	public String getDescription() {
	
		return "Any of the opponent's dice disc can be blocked for one go.";
	
	}

	public int getDefense() {
	
		return 4;
	
	}
	
	// onTurnStart: when the turn starts
	// Praetorianus: unblock all of the enemy's side
	public void onTurnStart(GameVisor gv, int playerId) {
		
		if (gv.whoseTurn() == getOwnerID()) {
			
			int enemy = (gv.whoseTurn() + 1) % Game.MAX_PLAYERS;
			
			for (int i = 0; i < Game.FIELD_SIZE; i++) {
			
				gv.getField().removeBlock(enemy, i);
			
			}
		
		}
		
	}

	@Override
	public CardParams getParams() {
		
		return new PraetorianusParams();
	
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		
		PraetorianusParams myParams = (PraetorianusParams)a;
		boolean performed = true;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		int position = myParams.getPositionToAttack();
		
		// Block the position
		g.getField().setBlock(enemy, position);
				
		return performed;
	
	}

}
