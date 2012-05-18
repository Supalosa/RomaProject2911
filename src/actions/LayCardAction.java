package actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import actiontargets.*;

import roma.*;
import cards.*;


public class LayCardAction extends PlayerAction {
	
	Card targetCard;
	int diceDisc;
	Map<Integer, ActionTarget<?>> myTargets;
	
	public static final int TARGET_CARD = 0;
	public static final int DICE_DISC = 1;
	 
	public LayCardAction() {
		myTargets = new HashMap<Integer, ActionTarget<?>>();
	}
	
	
	@Override
	public void initialise() {
		myTargets.put(TARGET_CARD, new CardInHandActionTarget("Please select the Card you want to lay"));
		myTargets.put(DICE_DISC, new DiceDiscActionTarget("And the dice disc you want to place it next to"));
		

	}
		
	public void onParameterSet (int paramIndex, ActionTarget<?> param) {
		
		if (paramIndex == TARGET_CARD) {
			
			targetCard = (Card)param.getValue();
			
		} else if (paramIndex == DICE_DISC) {
			
			diceDisc = (Integer)param.getValue();
			
		}
	}
		
	public boolean isValid() {
		
		boolean isValid = true;
		
		int i = diceDisc; // DUMMY VALUES
		
		if (i < 1 || i > Game.FIELD_SIZE) {
			isValid = false;
			g.getController().showMessage("Cannot lay that card, that disc value is not possible");
		} else if (g.getField().isBlocked(g.whoseTurn(), diceDisc-1)) {
			g.getController().showMessage("Cannot lay that card, that disc is blocked!");
			isValid = false;
		}
		
		
		return isValid;
		
	}

	@Override
	public void execute() {
		
		if (isValid()) {

			g.getCurrentPlayer().getHand().removeElement(targetCard);
			if (g.getField().getCard(g.whoseTurn(), diceDisc - 1) != null) {
				g.discard(g.getField().getCard(g.whoseTurn(), diceDisc - 1));				
			}
			g.getField().setCard(g.whoseTurn(), diceDisc - 1, targetCard);

		}
		
	}

	@Override
	public String getDescription() {
		return "Lay Card";
	}

	// Action is never visible
	@Override
	public boolean isVisible() {
		return false;
	}



	@Override
	public Map<Integer, ActionTarget<?>> getParameters() {
		return myTargets;
	}
	
}
