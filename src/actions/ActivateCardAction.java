package actions;

import java.util.*;
import actiontargets.*;
import roma.*;
import cards.*;

public class ActivateCardAction extends PlayerAction  {
	
	int targetPos;
	Card targetCard;
	int bribeDice;
	
	public static final int ACTIVATED_CARD = 0;
	public static final int BRIBE_DICE = 1;
	

	public void initialise () {
		addParameter(ACTIVATED_CARD, new CardOnFieldActionTarget("Select a card to activate:", true));
		
	}
	
	public void onParameterSet (int paramIndex, ActionTarget<?> param) {
		
		if (paramIndex == ACTIVATED_CARD) {
			
			targetCard = (Card)param.getValue();
			targetPos = getVisor().getField().getPosition(targetCard);
			
			// bribe disc?
			if (targetPos == Game.BRIBE_DISC) {
				addParameter(BRIBE_DICE, new DiceRollActionTarget("Select the die to use on the Dice Disc"));
			}
			
		} else if (paramIndex == BRIBE_DICE) {
			
			bribeDice = (Integer)param.getValue();
			
		}
	}

	public boolean isValid() {
		
		boolean valid = true;
		
		boolean hasDice = false;
		boolean hasBribe = false;
		for (int i : getVisor().getDiceRolls()) {
			
			if (i == targetPos) {
				
				hasDice = true;
				
			} else if (i == bribeDice) {
				
				hasBribe = true;
				
			}
			
		}
		if (targetPos == Game.BRIBE_DISC) {
			if (hasBribe == false) {
				getVisor().getController().showMessage("You don't have the dice you chose to bribe with.");
				valid = false;
			} else if (getVisor().getCurrentPlayer().getMoney() < bribeDice) {
				getVisor().getController().showMessage("You don't have enough sestertii to activate the Bribe Disc!");
				valid = false;
			}
		
		} else if (!hasDice) { // not bribe disc, and dont have dice
			
			getVisor().getController().showMessage("You don't have a dice corresponding to the dice [" + targetPos + "]");
			valid = false;
		
		} else if (getVisor().getField().isBlocked(getVisor().whoseTurn(), targetPos-1)) {
		
			getVisor().getController().showMessage("That disc is blocked!");
			valid = false;
		
		}
		
		if (targetCard == null) { // no card selected
			
			valid = false;
			getVisor().getController().showMessage("You don't have a card at that dice disc.");
		
		}
		return valid;
	}

	public void execute() {

		if(isValid()) {
			
			if (targetPos == Game.BRIBE_DISC) {
				
				if (targetCard.performEffect(getVisor(), targetPos)) {
					
					getVisor().getCurrentPlayer().setVP(getVisor().getCurrentPlayer().getVP() - bribeDice);
					getVisor().useDice(bribeDice);
					// TODO what about the targetPos dice?
					
				}
				
			} else if (targetCard.performEffect(getVisor(), targetPos)) {
				
				getVisor().useDice(targetPos);
				
			}
			
		} else {
			getVisor().getController().showMessage("Activation failed.");
		}
		
	}

	public String getDescription() {
		return "Activate Card";
	}


	// This action is only visible if we have a card on the field that has trigger EffectTrigger.TriggerOnActivate
	// and we have dice
	public boolean isVisible() {
		boolean show = false;
		for (Card cardOnField : getVisor().getField().getSideAsList(getVisor().whoseTurn())) {
			if (cardOnField != null) {
				show = true;
			}
		}
		boolean hasDice = false;
		
		for (int i = 0; i < getVisor().getDiceRolls().length; i++) {
			if (getVisor().getDiceRolls()[i] != 0) {
				hasDice = true;
			}
		}
		return show && hasDice;	
	}


}
