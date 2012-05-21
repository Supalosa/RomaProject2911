package adapters.activators;

import actions.ActivateCardAction;
import roma.Game;
import roma.MockController;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

public abstract class GenericAdapterActivator implements CardActivator {


	private Card theCard;
	private int fieldPosition;
	private Game game;
	private MockController controller;
	private boolean isCopy; // is a copy created by Scaenicus
	private GenericAdapterActivator copier; // the activator that copied this activator
	
	// bribe?
	private int bribeDice;
	private static final int NO_BRIBE = -1;
	
	public GenericAdapterActivator (int fieldPosition, Game game, Card theCard) {
		
		this.theCard = theCard;
		this.game = game;
		this.fieldPosition = fieldPosition;
		this.controller = (MockController)game.getController();
		this.bribeDice = NO_BRIBE;
		this.isCopy = false;
		this.copier = null;
		
	}
	
	public GenericAdapterActivator getCopier() {
		return copier;
	}

	public void setCopier(GenericAdapterActivator copier) {
		this.copier = copier;
	}

	@Override
	public abstract void complete();
	
	public void setIsCopy(boolean result) {
		
		isCopy = result;
		
	}
	
	/**
	 * A wrapper used to execute, because ActivateCardAction has incompatibilities
	 */
	public void execute(CardParams params) {
		
		// do NOT activate if this is a copy - scaenicus will do it! (we just give the params to the scaenicus)
		if (isCopy == false) {
			
			ActivateCardAction action = new ActivateCardAction(params);
			action.setDiceDisc(fieldPosition+1);
			//System.out.println ("GenericAdapterActivator:execute: execuitng at " + fieldPosition + " (" + bribeDice + ")");
		
			
			if (fieldPosition == Game.BRIBE_DISC) {
				action.setBribeDice(bribeDice);
				action.setUseBribe(true);
			} else {
				action.setUseBribe(false);
			}
			
			action.setActionExecutor(game.whoseTurn());
			
			// don't execute if blocked
			if (!game.getField().isBlocked(game.whoseTurn(), fieldPosition-1)) {
				action.execute(game.getGameVisor(), theCard);
			}
		} else { // this is a copy - apply to the scaenicus
			
			ScaenicusAdapterActivator scaenicusActivator = (ScaenicusAdapterActivator)getCopier();
			
			scaenicusActivator.getParams().setCopiedParams(params);
			scaenicusActivator.getParams().setCopiedCard(theCard.getID());
			
		}
	}
	
	public void setBribe(int dice) {
		
		this.bribeDice = dice;
		
	}
	public int getFieldPosition() {
		return fieldPosition;
	}


	public void setFieldPosition(int fieldPosition) {
		this.fieldPosition = fieldPosition;
	}


	public Game getGame() {
		return game;
	}


	public void setGame(Game game) {
		this.game = game;
		this.controller = (MockController)game.getController();
	}


	public MockController getController() {
		return controller;
	}


	public void setController(MockController controller) {
		this.controller = controller;
	}
	

	public Card getTheCard() {
		return theCard;
	}


	public void setTheCard(Card theCard) {
		this.theCard = theCard;
	}

	
}
