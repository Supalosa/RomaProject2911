package cards;

import cards.activators.CardParams;
import cards.activators.TribunusPlebisParams;
import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardTribunusPlebis extends Card {

	@Override
	public CardNames getID() {
		
		return CardNames.Tribunus_Plebis;
	
	}

	@Override
	public int getCostToPlay() {
	
		return 5;
	
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
	
		return "Tribunus Plebis";
	
	}

	@Override
	public String getDescription() {
	
		return "The player gets 1 victory point from their opponent";
	
	}

	@Override
	public int getDefense() {
	
		return 5;
	
	}

	@Override
	public CardParams getParams() {

		return new TribunusPlebisParams();
	
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
	
		boolean performed = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		g.getCurrentPlayer().setVP(g.getCurrentPlayer().getVP() + 1);
		g.getPlayer(enemy).setVP(g.getPlayer(enemy).getVP() - 1);
		
		performed = true;
		
		return performed;
	
	}

}
