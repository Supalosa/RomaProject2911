package cards;

import java.util.*;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardConsul extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Consul;
	}

	@Override
	public int getCostToPlay() {
		return 3;
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
		return "Consul";
	}

	@Override
	public String getDescription() {
		return  "The score on an action die which has not yet been used can be " +
				"increased or decreased by 1 point.";
	}

	@Override
	public int getDefense() {
		return 3;
	}

	@Override
	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	@Override
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
			boolean increase;
			diceRoll = g.getController().getInt("Enter of the value of the dice roll you want to change:");
			while (diceRolls.contains(diceRoll) == false) {
				diceRoll = g.getController().getInt("You don't have that die. Enter of the value of the dice roll you want to change:");
	
			}
			
	
			increase = g.getController().getBoolean("Do you want to increase (Y) or decrease (N) the value of that die?");
			if (increase && diceRoll == 6) {
				g.getController().showMessage("You cannot increase the value of a 6 die.");
			} else if (!increase && diceRoll == 1) {
				g.getController().showMessage("You cannot decrease the value of a 1 die.");
			} else {
				if (increase) {
					g.setDiceRoll(diceRoll, diceRoll+1);
				} else {
					g.setDiceRoll(diceRoll, diceRoll-1);
				}
				activated = true;
			}
		} else {
			g.getController().showMessage("You cannot activate this card, because you will not have any dice available.");
		}
		
		return activated;

	}

}
