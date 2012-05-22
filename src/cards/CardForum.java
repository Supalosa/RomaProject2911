package cards;

import cards.activators.CardParams;
import cards.activators.ForumParams;
import roma.*;
import enums.*;

public class CardForum extends Card {
	
	public CardNames getID() {
		
		return CardNames.Forum;
	
	}

	public int getCostToPlay() {
	
		return 5;
	
	}

	public int getDiceToActivate() {
	
		return 2;
	
	}

	public boolean isBuilding() {
	
		return true;
	
	}

	public String getName() {
		
		return "Forum";
	
	}

	public String getDescription() {
	
		return "Requires 2 action dice: one to activate the Forum and the other" +
				" to determine how many victory points the player receives.";
	
	}

	public int getDefense() {
	
		return 5;
	
	}

	@Override
	public CardParams getParams() {
	
		return new ForumParams();
	
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {

		ForumParams myParams = (ForumParams)a;
		int vpIncrease;
		
		if (g.getNumDiceRolls() > 1) {
			
			g.useDice(myParams.getForumDie());
			vpIncrease = myParams.getForumDie();
			
			// check basilica right
			if (pos < Game.FIELD_SIZE - 1 && g.getField().getCard(g.whoseTurn(), pos) != null &&
					g.getField().getCard(g.whoseTurn(), pos).getID() == CardNames.Basilica) {
				
				g.getController().showMessage("You have a Basilica on the right of the activated Forum.");
				g.getController().showMessage("Adding 2 more VP to your haul");
				vpIncrease += 2;
			
			}
			
			// check basilica left
			if (pos > 1 && g.getField().getCard(g.whoseTurn(), pos-2) != null &&
					g.getField().getCard(g.whoseTurn(), pos-2).getID() == CardNames.Basilica) {
			
				g.getController().showMessage("You have a Basilica on the left of the activated Forum.");
				g.getController().showMessage("Adding 2 more VPs to your haul");
				vpIncrease += 2;
			
			}
			
			if (myParams.isUseTemplum()) {
			
				boolean hasTemplum = false;
				
				// find a templum...
				
				if (pos > 1) {
				
					Card left = g.getField().getCard(g.whoseTurn(), pos-2);
					
					if (left != null && left.getID() == CardNames.Templum) {
					
						hasTemplum = true;
					
					}
				
				}
				
				if (pos < Game.FIELD_SIZE - 1) {
				
					Card right = g.getField().getCard(g.whoseTurn(), pos);
					
					if (right != null && right.getID() == CardNames.Templum) {
					
						hasTemplum = true;
					
					}
				
				}
				
				if (hasTemplum) {
				
					int[] rolls = g.getDiceRolls();
					boolean removedActivator = false;
				
					for (int i = 0; i < rolls.length; i++) {
						
						if (rolls[i] == pos && removedActivator == false) {
					
							removedActivator = true;
						
						} else if (rolls[i] != 0) {
							
							vpIncrease += g.getDiceRoll(i);
							g.getController().showMessage("Added " + g.getDiceRoll(i) + " VPs to your haul");
							g.useDice(g.getDiceRoll(i));
						
						}
						
					}
					
				} else { // no templum but it was activated... time paradox
					
					g.getController().showMessage("Oh dear! You caused a Time Paradox by activating a" +
												" Forum and requesting a Templum where there is none.");
					g.onTimeParadox();					
					
				}
				
			}
			
			// add VP
			g.getCurrentPlayer().setVP(g.getCurrentPlayer().getVP() + vpIncrease);
			g.getController().showMessage("You gained " + vpIncrease + " VPs");

		} else {
			
			g.getController().showMessage("You don't have enough dice to activate this card");
		
		}
		
		return true;

	}

}
