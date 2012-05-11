package actions;

import java.util.List;

import roma.*;
import cards.*;

public class ActivateCardAction implements IPlayerAction {
	
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
		
		if (!valid) {
			game.getController().showMessage("You don't have a dice corresponding to the dice [" + targetPos + "]");
		}
		
		if (targetCard == null) {
			valid = false;
			game.getController().showMessage("no card?");
		}
		
		List<String> blocks = g.getField().getBlocks();
		
		for (String s : blocks) {
			
			if (s.charAt(0) == g.whoseTurn() && s.charAt(1) == targetPos) {
				
				valid = false;
				g.getController().showMessage("That dice disc is blocked for this turn");
			}
			
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

	// This action is only visible if we have a card on the field that has trigger EffectTrigger.TriggerOnActivate
	// and we have dice
	public boolean isVisible(GameVisor g) {
		boolean show = false;
		for (Card cardOnField : g.getField().getSideAsList(g.whoseTurn())) {
			if (cardOnField != null) {
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
