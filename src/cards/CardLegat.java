package cards;

import cards.activators.CardParams;
import cards.activators.LegatParams;
import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardLegat extends Card {

	public CardNames getID() {
		
		return CardNames.Legat;
	
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
	
		return "Legat";
	
	}

	public String getDescription() {
	
		return "A player gets 1 victory point from the stockpile " +
				"for every dice disc not occupied by the opponent.";
	
	}

	public int getDefense() {
	
		return 2;
	
	}


	@Override
	public CardParams getParams() {
	
		return new LegatParams();
	
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
	
		boolean performed = false;
		
		int enemyId = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		int points = 0;
		
		for (int i = 0 ; i < Game.FIELD_SIZE; i++) {
			
			if (g.getField().getCard(enemyId, i) == null) {
				
				points++;
				
			}
			
		}
		
		g.getCurrentPlayer().setVP(g.getCurrentPlayer().getVP() + points);
		
		g.getController().showMessage("You gained " + points + " points from Legat.");
		performed = true;
		
		return performed;
	
	}

}
