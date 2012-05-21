package cards;

import java.util.ArrayList;
import java.util.List;

import cards.activators.CardParams;
import cards.activators.OnagerParams;
import cards.activators.VelitesParams;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardVelites extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Velites;
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
		return "Velites";
	}

	@Override
	public String getDescription() {
		return "Attacks any opposing character card (does not have to be directly opposite)." +
				" The battle die is thrown once.";
	}

	@Override
	public int getDefense() {
		return 3;
	}
	

	@Override
	public CardParams getParams() {
		return new VelitesParams();
	}

	@Override
	public boolean performEffect(GameVisor g, int pos, CardParams a) {
		VelitesParams myParams = (VelitesParams)a;
		boolean performed = true;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		Card target = g.getField().getCard(enemy, myParams.getPositionToAttack());
		
		if (target != null && !target.isBuilding()) {
			int diceRoll =  myParams.getBattleDie();
			g.getController().showMessage("The battle die rolled a " + diceRoll);
			
			if (target.onAttacked(g, this, myParams.getPositionToAttack(), diceRoll)) {

				g.getController().showMessage("You killed a " + target.getName() + "!");
				
			} else {
				
				g.getController().showMessage("Could not kill the target, battle value was " + diceRoll + " / " + target.getRealDefense());
				
			}
		}
		return performed;
	}

}
