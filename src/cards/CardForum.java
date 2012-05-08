package cards;

import java.util.ArrayList;
import java.util.List;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

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

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g, int pos) {
		boolean activated = false;
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
			diceRoll = g.getController().getInt("Enter of the value of the dice roll corresponding to the amount of VP you want to gain:");
			while (diceRolls.contains(diceRoll) == false) {
				diceRoll = g.getController().getInt("You don't have that die. Enter the dice roll:");
	
			}
			
			// use dice
			g.useDice(diceRoll);
			activated = true;
			
			// check basilica right
			if (pos < Game.FIELD_SIZE - 1 && g.getField().getCard(g.whoseTurn(), pos).getID() == CardNames.Basilica) {
				diceRoll += 2;
			}
			
			// check basilica left
			if (pos > 1 && g.getField().getCard(g.whoseTurn(), pos-2).getID() == CardNames.Basilica) {
				diceRoll += 2;
			}
			
			// add VP
			g.getCurrentPlayer().setVP(g.getCurrentPlayer().getVP() + diceRoll);
			
			
			

		} else {
			g.getController().showMessage("You cannot activate this card, because you will not have any dice available.");
		}
		
		return activated;

	}

}
