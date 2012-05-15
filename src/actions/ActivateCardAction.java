package actions;

import java.util.List;

import roma.*;
import cards.*;

public class ActivateCardAction implements IPlayerAction {
	
	int targetPos;
	Card targetCard;
	GameVisor game;
	int bribeDice;
	boolean confirmBribe;
	
	
	public boolean isValid(GameVisor g) {
		
		boolean valid = true;
		
		boolean hasDice = false;
		boolean hasBribe = false;
		for (int i : game.getDiceRolls()) {
			
			if (i == targetPos) {
				
				hasDice = true;
				
			} else if (i == bribeDice) {
				
				hasBribe = true;
				
			}
			
		}
		if (targetPos == Game.BRIBE_DISC) {
			if (hasBribe == false) {
				game.getController().showMessage("You don't have the dice you chose to bribe with.");
				valid = false;
			} else if (g.getPlayer(g.whoseTurn()).getMoney() < bribeDice) {
				game.getController().showMessage("You don't have enough sestertii to activate the Bribe Disc!");
				valid = false;
			}
		
		} else if (!hasDice) { // not bribe disc, and dont have dice
			
			game.getController().showMessage("You don't have a dice corresponding to the dice [" + targetPos + "]");
			valid = false;
		
		} else if (g.getField().isBlocked(g.whoseTurn(), targetPos-1)) {
		
			game.getController().showMessage("That disc is blocked!");
			valid = false;
		
		}
		
		if (targetCard == null) { // no card selected
			
			valid = false;
			game.getController().showMessage("You don't have a card at that dice disc.");
		
		}
		return valid;
	}

	public void execute(GameVisor g) {
		
		game = g;
		query();
		
		if(isValid(game)) {
			
			if (targetPos == Game.BRIBE_DISC) {
				
				if (confirmBribe == false) {
					
					game.getController().showMessage("Action cancelled.");
					
				} else if (targetCard.performEffect(game, targetPos)) {
					
					game.getPlayer(g.whoseTurn()).setVP(game.getPlayer(g.whoseTurn()).getVP() - bribeDice);
					game.useDice(bribeDice);
					
				}
				
			} else if (targetCard.performEffect(game, targetPos)) {
				
				game.useDice(targetPos);
				
			}
			
		} else {
			game.getController().showMessage("Activation failed.");
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
		
		if (targetPos == Game.BRIBE_DISC) {
			bribeDice = game.getController().getInt("Choose the Dice you want to use to activate the bribe disc.");
			confirmBribe = game.getController().getBoolean("Are you sure you want to activate that disc? Doing so will cost extra Sestertii.");
		} else {
			confirmBribe = true;
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
