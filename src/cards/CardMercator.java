package cards;

import cards.activators.CardParams;
import cards.activators.MercatorParams;
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
	public CardParams getParams() {
		return new MercatorParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		
		MercatorParams myParams = (MercatorParams)a;
		boolean performed = true;
		int money = myParams.getMoneyToSpend();
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		// Note: according to acceptance, negative sestertii not possible.
		if ((money / 2) > g.getPlayer(enemy).getVP()) {
			money = g.getPlayer(enemy).getVP() * 2;
		}	
		
		g.getCurrentPlayer().setMoney(g.getCurrentPlayer().getMoney() - money);
		g.getPlayer(enemy).setMoney(g.getPlayer(enemy).getMoney() + money);
			
		g.getCurrentPlayer().setVP(g.getCurrentPlayer().getVP() + money / 2);
		g.getPlayer(enemy).setVP(g.getPlayer(enemy).getVP() - money / 2);
		
		
		return performed;
	}

}
