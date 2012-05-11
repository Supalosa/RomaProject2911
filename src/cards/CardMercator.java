package cards;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardMercator extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Mercator;
	}

	@Override
	public int getCostToPlay() {
		return 7;
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
		return "Mercator";
	}

	@Override
	public String getDescription() {
		return "For 2 sestertii each, the player can buy 1 victory point from their opponent as long " +
				"as there are money and victory points left! The opponent gets the money";
	}

	@Override
	public int getDefense() {
		return 2;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		
		int money = 0;
		
		boolean valid = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		while (!valid) {
			
			money = g.getController().getInt("How much sestertii do you want to spend?");
			
			if (money <= g.getCurrentPlayer().getMoney()) {
				
				if (money / 2 <= g.getPlayer(enemy).getVP()) {
					
					valid = true;
					g.getCurrentPlayer().setMoney(g.getCurrentPlayer().getMoney() - money);
					g.getCurrentPlayer().setVP(g.getCurrentPlayer().getVP() + money / 2);
					g.getPlayer(enemy).setVP(g.getPlayer(enemy).getVP() - money / 2);
					performed = true;
				
				} else {
					
					g.getController().showMessage("Your opponent doesn't have that many VP"); 
					
				}
				
			} else {
				
				g.getController().showMessage("You don't have that much sestertii");
			
			}
			
		}
		
		
		
		return performed;
		
	}

}
