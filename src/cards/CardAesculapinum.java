package cards;

import java.util.*;

import roma.GameVisor;
import enums.CardNames;
import enums.EffectTrigger;

public class CardAesculapinum extends Card {
	
	public CardNames getID() {
		return CardNames.Aesculapinum;
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
		return "Aesculapinum";
	}

	public String getDescription() {
		return "The temple of Asculapius (the God of healing) enables the player to pick" +
				" up any character card from the discard pile and add it to their hand.";
	}

	public int getDefense() {
		return 2;
	}

	public EffectTrigger getEffectTrigger() {
		return EffectTrigger.TriggerOnActivate;
	}

	public boolean performEffect(GameVisor g, int pos) {
		
		boolean performed = false;
		
		if (!g.getDiscardPile().getStack().isEmpty()) {
			
			List<Card> characters = new ArrayList<Card>();
			
			for (Card c : g.getDiscardPile().getStack()) {
				
				if (!c.isBuilding()) {
					
					characters.add(c);
					
				}
				
			}
			
			if (!characters.isEmpty()) {
				
				Card selected = g.getController().getCard(characters, "Pick the card you wish to add to your hand");

				g.getCurrentPlayer().addCard(selected);
				g.getDiscardPile().getStack().remove(selected);

				performed = true;
				
			}

		} else {
			g.getController().showMessage("The discard pile is empty.");
		}
		
		return performed;

	}


}

