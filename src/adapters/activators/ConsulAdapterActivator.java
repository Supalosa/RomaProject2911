package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the TribunusPlebis.
 * @author Supalosa
 *
 */
public class ConsulAdapterActivator implements ConsulActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	boolean increase;
	int changedDice;
	
	public ConsulAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
	}
	
	
	
	@Override
	public void complete() {
		
		PlayerAction action = new ActivateCardAction();
		MockController controller = (MockController)game.getController();
		
		controller.insertInput(Integer.toString(fieldPosition));
		
		controller.insertInput(Integer.toString(changedDice));
		
		if (increase == true) {
			controller.insertInput("Y");
		} else {
			controller.insertInput("N");
		}
		
		action.execute(game.getGameVisor());
	
	}



	/**
	 * We send Y if increasing value, N if decreasing
	 */
	@Override
	public void chooseConsulChangeAmount(int amount) {
		if (amount > 0) {
			increase = true;
		} else {
			increase = false;
		}
		
	}


	
	@Override
	public void chooseWhichDiceChanges(int originalRoll) {
		changedDice = originalRoll;
	}

}
