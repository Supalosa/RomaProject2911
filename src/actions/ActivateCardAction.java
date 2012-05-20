package actions;

import roma.*;
import cards.*;
import cards.activators.*;

public class ActivateCardAction implements IPlayerAction {
	
	int targetPos;
	//Card targetCard;
	GameVisor game;
	CardParams params;
	int bribeDice;
	boolean confirmBribe;
	
	public ActivateCardAction(CardParams params) {
		targetPos = -1;
		this.params = params;
		
	}

	@Override
	public String describeParameters() {
		return "targetPos: " + targetPos + ", bribeDice: " + bribeDice + ",  confirmBribe: " + confirmBribe;
	}
	
	public boolean isValid(GameVisor g) {
		
		boolean valid = true;
		
		boolean hasDice = false;
		boolean hasBribe = false;
		
		/*
		 * if we have params already, ignore everything... (HACK.. not sure if this is correct TODO)
		 */
		
		if (params == null) {
			
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
			
			if (targetPos == -1) { // no card selected
				
				valid = false;
				game.getController().showMessage("You don't have a card at that dice disc.");
			
			} else if (params == null) { // no params (unactivatable card)
				valid = false;
				game.getController().showMessage("You cannot activate that card.");
	
			} else if (!params.isValid()) {
				valid = false;
				game.getController().showMessage(params.getError());
				
			}
		}
		
		return valid;
	}

	public void execute(GameVisor g, Card targetCard) {
		
		game = g;
		
		if(isValid(game)) {
			if (targetCard == null) {
				targetCard = g.getField().getCard(g.whoseTurn(), targetPos-1);
			}
			//System.out.println ("ActivateCardAction (" + targetPos + "): " + targetCard );
			if (targetPos == Game.BRIBE_DISC) {
				//System.out.println ("BribeDice: before " + game.getCurrentPlayer().getMoney() + " (-" + bribeDice + ")");

				if (confirmBribe == false) {
					
					game.getController().showMessage("Action cancelled.");
					
				} else if (targetCard.performEffect(game, targetPos, params)) {
					if (bribeDice > 0) {
						game.getPlayer(g.whoseTurn()).setMoney(game.getPlayer(g.whoseTurn()).getMoney() - bribeDice);
					
						game.useDice(bribeDice);
					}
				}
				//System.out.println ("BribeDice: after " + game.getCurrentPlayer().getMoney());
				
			} else if (targetCard.performEffect(game, targetPos, params)) {
				
				game.useDice(targetPos);
				
			}
			
			
			
		} else {
			game.getController().showMessage("Activation failed.");
		}
		
		
		// Log the action
		g.getActionLogger().addAction(this, game.getTurnNumber());
	}
	

	/**
	 * "normal" execute
	 */
	@Override
	public void execute(GameVisor g) {
		execute(g, null);
		
	}

	public String getDescription() {
		return "Activate Card";
	}

	public void query(GameVisor g) {
		
		g.getController().showField();
		
		
		targetPos = g.getController().getInt("Choose the Dice Disc you want to activate");
		if (targetPos >= 1 && targetPos <= Game.FIELD_SIZE) {
			Card targetCard = g.getField().getCard(g.whoseTurn(),targetPos-1);
			
			
			if (targetPos == Game.BRIBE_DISC) {
				bribeDice = g.getController().getInt("Choose the Dice you want to use to activate the bribe disc.");
				confirmBribe = g.getController().getBoolean("Are you sure you want to activate that disc? Doing so will cost extra Sestertii.");
			} else {
				confirmBribe = true;
			}
			
			// Get the card params
			if (params == null && targetCard != null) {
				params = targetCard.getParams();
				if (params != null) {
					params.query(g,  targetPos);
				}
			}
		} else {
			g.getController().showMessage("Invalid dice disc.");
		}
			
	}
	
	public void setDiceDisc(int disc) {
		targetPos = disc - 1;
	}
	
	public void setUseBribe(boolean use) {
		
		confirmBribe = use;
		
	}
	
	public void setBribeDice(int dice) {
		
		bribeDice = dice;
		
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
