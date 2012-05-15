package cards;

import java.util.*;
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


	public boolean performEffect(GameVisor g, int pos) {
		
		if (g.getNumDiceRolls() > 1) {
			// dice rolls that exclude the one that activated this card.
			List<Integer> diceRolls = new ArrayList<Integer>();
			boolean removedActivator = false;
			for (int i = 0; i < g.getNumDiceRolls(); i++) {
				if (g.getDiceRoll(i) != pos || removedActivator == true) {
					diceRolls.add(g.getDiceRoll(i));
				} else {
					removedActivator = true;
				}
			}
			
			g.getController().showMessage("Available dice: " + diceRolls.toString());
			
			int diceRoll;
			diceRoll = g.getController().getInt("Enter of the value of the dice roll " +
								"corresponding to the amount of VP you want to gain:");
			while (diceRolls.contains(diceRoll) == false) {
				diceRoll = g.getController().getInt("You don't have that die. Enter the dice roll:");
	
			}
			
			// use dice
			g.useDice(pos);
			g.useDice(diceRoll);
			
			// check basilica right
			if (pos < Game.FIELD_SIZE - 1 && g.getField().getCard(g.whoseTurn(), pos) != null &&
					g.getField().getCard(g.whoseTurn(), pos).getID() == CardNames.Basilica) {
				g.getController().showMessage("You have a Basilica on the right of the activated Forum.");
				g.getController().showMessage("Adding 2 more VP to your haul");
				diceRoll += 2;
			}
			
			// check basilica left
			if (pos > 1 && g.getField().getCard(g.whoseTurn(), pos-2) != null &&
					g.getField().getCard(g.whoseTurn(), pos-2).getID() == CardNames.Basilica) {
				g.getController().showMessage("You have a Basilica on the left of the activated Forum.");
				g.getController().showMessage("Adding 2 more VPs to your haul");
				diceRoll += 2;
			}
			
			if ((pos < Game.FIELD_SIZE - 1 && g.getField().getCard(g.whoseTurn(), pos) != null &&
					g.getField().getCard(g.whoseTurn(), pos).getID() == CardNames.Templum)
					|| (pos > 1 && g.getField().getCard(g.whoseTurn(), pos) != null &&
							g.getField().getCard(g.whoseTurn(), pos-2).getID() == CardNames.Templum)) {
				
				g.getController().showMessage("You have a Templum adjecent to the activated Forum.");
				if (g.getController().getBoolean("Do you wish to use your remaining dice and " +
												"add its face value to your haul?")) {
					
					int[] rolls = g.getDiceRolls();
					
					for (int i = 0; i < rolls.length; i++) {
						
						if (rolls[i] != 0) {
							
							diceRoll += g.getDiceRoll(i);
							g.getController().showMessage("Added " + g.getDiceRoll(i) + " VPs to your haul");
							g.useDice(g.getDiceRoll(i));
							
						}
						
					}
					
				}
				
			}
			
			// add VP
			g.getCurrentPlayer().setVP(g.getCurrentPlayer().getVP() + diceRoll);
			g.getController().showMessage("You gained " + diceRoll + " VPs");


		} else {
			
			g.getController().showMessage("You don't have enough dice to activate this card");
		
		}
		
		return false;

	}

}
