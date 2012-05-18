package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.ConsulParams;
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
	ConsulParams params;
	
	public ConsulAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.params = new ConsulParams();
		
	}
	
	
	
	@Override
	public void complete() {
		
		IPlayerAction action = new ActivateCardAction(params);
		MockController controller = (MockController)game.getController();
		
		controller.insertInput(Integer.toString(fieldPosition));
		
		action.execute(game.getGameVisor());
	
	}



	/**
	 * We send Y if increasing value, N if decreasing
	 */
	@Override
	public void chooseConsulChangeAmount(int amount) {
		if (amount > 0) {
			params.setIncreaseDice(true);
		} else {
			params.setIncreaseDice(false);
		}
	}


	
	@Override
	public void chooseWhichDiceChanges(int originalRoll) {
		params.setDiceValue(originalRoll);
	}

}
