package adapters.activators;

import java.util.*;

import actions.*;
import roma.*;
import cards.Card;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Consiliarius.
 * @author Supalosa
 *
 */
public class ConsiliariusAdapterActivator implements ConsiliariusActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	boolean increase;
	int changedDice;
	List<DiceCardPair> laidCards;
	
	public ConsiliariusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.laidCards = new ArrayList<DiceCardPair>();
	}
	
	
	
	@Override
	public void complete() {
		PlayerAction action = new ActivateCardAction();
		MockController controller = (MockController)game.getController();
		
		controller.insertInput(Integer.toString(fieldPosition));
		
		// For all the characters on the field
		List<DiceCardPair> fieldCharacters = new ArrayList<DiceCardPair>();
		
		for (Card c : game.getField().getSideAsList(game.whoseTurn())) {
			
			if (!c.isBuilding()) {
				
				//fieldCharacters.add()
				
			}
		}
		
		
		action.execute(game.getGameVisor());
	}



	/**
	 * 
	 */
	@Override
	public void placeCard(framework.cards.Card card, int diceDisc) {
		laidCards.add(new DiceCardPair(card, diceDisc));
	}
	
	private class DiceCardPair {
		private framework.cards.Card card;
		private int diceDisc;
		
		public DiceCardPair (framework.cards.Card card, int diceDisc) {
			this.diceDisc = diceDisc;
			this.card = card;
		}
		
		public int getDiceDisc() {
			return diceDisc;
		}
		public void setDiceDisc(int diceDisc) {
			this.diceDisc = diceDisc;
		}

		
	}


}
