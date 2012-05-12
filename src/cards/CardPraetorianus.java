package cards;

import java.util.*;

import modifiers.IModifier;
import modifiers.PraetorAura;
import roma.Game;
import roma.GameVisor;
import enums.CardNames;

public class CardPraetorianus extends Card {

	public CardNames getID() {
		return CardNames.Praetorianus;
	}

	public int getCostToPlay() {
		return 4;
	}

	public int getDiceToActivate() {
		return 1;
	}

	public boolean isBuilding() {
		return false;
	}

	public String getName() {
		return "Praetorianus";
	}

	public String getDescription() {
		return "Any of the opponent's dice disc can be blocked for one go.";
	}

	public int getDefense() {
		return 4;
	}

	@Override
	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		
		int enemy = (g.whoseTurn() + 1) % Game.MAX_PLAYERS;
		
		g.getController().showField();
		
		int diceDisc = g.getController().getInt("Which dice disc do you wish to block?");
		
		while (diceDisc < 1 || diceDisc > 7) {
			diceDisc = g.getController().getInt("Invalid dice disc. Which dice disc do you wish to block?");
		}
		
		// Cast the aura onto the field.
		IModifier praetorAura = new PraetorAura(enemy, diceDisc-1);
		castModifier(g.getField(), praetorAura);
		
		return performed;
		
	}
	
	// onTurnStart: when the turn starts
	public void onTurnStart(GameVisor gv, int playerId) {
		
		if (gv.whoseTurn() == getOwnerID()) {
			
			List<IModifier> remove = new ArrayList<IModifier>();
			List<IModifier> modifiersOnField = gv.getField().getModifiers();
			
			for (IModifier m : modifiersOnField) {
				
				if (m.getCaster() == this) {
					
					remove.add(m);
					
				}
			}
			
			for (IModifier mod : remove) {
				
				gv.getField().removeModifier(mod);
				
			}
			
		}
		
	}

}
