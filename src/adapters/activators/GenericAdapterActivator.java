package adapters.activators;

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
	
	// bribe?
	private int bribeDice;
	private static final int NO_BRIBE = -1;
	
	public GenericAdapterActivator (int fieldPosition, Game game, Card theCard) {
		
		this.theCard = theCard;
		this.game = game;
		this.fieldPosition = fieldPosition;
		this.controller = (MockController)game.getController();
		this.bribeDice = NO_BRIBE;
		
	}
	
	@Override
	public abstract void complete();
	
	/**
	 * A wrapper used to execute, because ActivateCardAction has incompatibilities
	 */
	public void execute(CardParams params) {
		
		// stop if disc is blocked
		if (!game.getField().isBlocked(game.whoseTurn(), fieldPosition-1)) {	
			if (bribeDice != NO_BRIBE) { // bribe?
				System.out.println ("BribeDice: before " + game.getCurrentPlayer().getMoney() + " (-" + bribeDice + ")");
				if (theCard.performEffect(game.getGameVisor(), fieldPosition, params)) {
					
					game.getCurrentPlayer().setMoney(game.getCurrentPlayer().getMoney() - bribeDice);
					game.useDice(bribeDice);
				}
				System.out.println ("BribeDice: after " + game.getCurrentPlayer().getMoney());
				
			} else if (theCard.performEffect(game.getGameVisor(), fieldPosition, params)) {
					
				game.useDice(fieldPosition);
					
			}
		} else { // disc was blocked
			System.out.println ("acceptance: did not activate " + theCard + ", disc is blocked");
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
