package cards;

import java.util.List;

import cards.activators.CardParams;
import cards.activators.MercatusParams;

import roma.*;
import enums.*;

public class CardMercatus extends Card {
	
	public CardNames getID() {
	
		return CardNames.Mercatus;
	
	}

	public int getCostToPlay() {
	
		return 6;
	
	}

	public int getDiceToActivate() {
	
		return 1;
	
	}

	public boolean isBuilding() {
	
		return true;
	
	}

	public String getName() {
	
		return "Mercatus";
	
	}

	public String getDescription() {
	
		return "The player gets 1 victory point for every" +
				" face-up Forum that the opponent has.";
	
	}

	public int getDefense() {
	
		return 3;
	
	}

	@Override
	public CardParams getParams() {
	
		return new MercatusParams();
	
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		
		boolean performed = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		List<Card> enemyField = g.getField().getSideAsList(enemy);
		
		int forums = 0;
		
		for (Card c : enemyField) {
			
			if (c.getID() == CardNames.Forum) {
				
				forums++;
				
			}
			
		}
		
		g.getCurrentPlayer().setVP(g.getCurrentPlayer().getVP() + forums);
		g.getPlayer(enemy).setVP(g.getPlayer(enemy).getVP() - forums);
		
		performed = true;
		
		return performed;
	
	}

}
