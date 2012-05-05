package cards;

import roma.Game;
import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardCenturio extends Card {

	@Override
	public CardNames getID() {
		return CardNames.Centurio;
	}

	@Override
	public int getCostToPlay() {
		return 9;
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
		return "Centurio";
	}

	@Override
	public String getDescription() {
		return "Attacks the card directly opposite, whether it is a character or building card. " +
				"The value of an unused action die can be added to the value of the battle die " +
				"(the action die is then counted as used). This is decided after the battle " +
				"die has been thrown.";
	}

	@Override
	public int getDefense() {
		return 5;
	}

	@Override
	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		
		if (g.getField().getCard((g.whoseTurn() + 1) % Game.MAX_PLAYERS, pos - 1) != null) {
			
			int battleDie = g.rollDice();
			
			if (battleDie < g.getField().getCard((g.whoseTurn() + 1) % Game.MAX_PLAYERS, pos - 1).getDefense()) {
				
				g.getController().showMessage("The battle die rolled a " + battleDie);
				
				g.getController().showDiceRolls();
				
				int add = g.getController().getInt("Do you wish to add to the value of the battle die?");
				
				if (add != -1) {
					
					battleDie += g.getDiceRoll()[add];
					
				}
				
			}
			
		} else {
			
			g.getController().showMessage("There is nothing to attack");
			
		}
		
		return performed;
	}

}
