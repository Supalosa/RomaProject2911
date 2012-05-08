package actions;

import roma.*;
import cards.*;
import enums.*;

public class ActivateCardAction implements PlayerAction {
	
	int targetPos;
	Card targetCard;
	GameVisor game;
	
	public boolean isValid(GameVisor g) {
		
		boolean valid = false;
		
		for (int i : game.getDiceRolls()) {
			
			if (i == targetPos) {
				
				valid = true;
				
			}
			
		}
		
		if (targetCard == null) {
			valid = false;
		}

		return valid;
	}

	public void execute(GameVisor g) {
		game = g;
		query();
		
		if(isValid(game)) {
			
			if (targetCard.performEffect(game, targetPos)) {
				
				game.useDice(targetPos);
				
			}
			
		} else {
			game.getController().showMessage("You don't have a card corresponding to the dice [" + targetPos + "]");
		}
		
	}

	public String getDescription() {
		return "Activate Card";
	}

	public void query() {
		
		game.getController().showField();
		
		targetPos = game.getController().getInt("Choose the Dice Disc you want to activate");
		if (targetPos >= 1 && targetPos <= Game.FIELD_SIZE) {
			targetCard = game.getField().getCard(game.whoseTurn(),targetPos-1);
		}
		
	}

	// THis action is only visible if we have a card on the field that has trigger EffectTrigger.TriggerOnActivate
	// and we have dice
	public boolean isVisible(GameVisor g) {
		boolean show = false;
		for (Card cardOnField : g.getField().getSideAsList(g.whoseTurn())) {
			if (cardOnField != null && cardOnField.getEffectTrigger() == EffectTrigger.TriggerOnActivate) {
				show = true;
			}
		}
		boolean hasDice = false;
		
		for (int i = 0; i < g.getDiceRolls().length; i++) {
			if (g.getDiceRolls()[i] != 0) {
				hasDice = true;
			}
		}
		return show && hasDice;	
	}

}
