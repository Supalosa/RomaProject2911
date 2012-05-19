package cards;

import java.util.ArrayList;
import java.util.List;

import cards.activators.CardParams;
import cards.activators.OnagerParams;

import enums.*;

import roma.*;

public class CardOnager extends Card {
	
	public CardNames getID() {
		return CardNames.Onager;
	}

	public int getCostToPlay() {
		return 5;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return true;
	}

	public String getName() {
		return "Onager";
	}

	public String getDescription() {
		return "This Roman catapult attacks any opposing building. " +
				"The battle die is thrown once.";
	}

	public int getDefense() {
		return 4;
	}
	
	@Override
	public CardParams getParams() {
		return new OnagerParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		OnagerParams myParams = (OnagerParams)a;
		boolean performed = true;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card target = g.getField().getCard(enemy, myParams.getPositionToAttack());
		
		if (target != null && target.isBuilding()) {
			int diceRoll =  myParams.getBattleDie();
			g.getController().showMessage("The battle die rolled a " + diceRoll);
			
			if (target.onAttacked(g, this, myParams.getPositionToAttack(), diceRoll)) {

				g.getController().showMessage("You killed a " + target.getName() + "!");
				
			} else {
				
				g.getController().showMessage("Could not kill the target, battle value was " + diceRoll);
				
			}
		}
		return performed;
	}


}
