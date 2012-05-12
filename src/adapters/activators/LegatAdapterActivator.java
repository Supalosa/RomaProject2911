package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Legat.
 * @author Supalosa
 *
 */
public class LegatAdapterActivator implements LegatActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	
	public LegatAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
	}
	
	
	
	@Override
	public void complete() {
		
		IPlayerAction action = new ActivateCardAction();
		MockController controller = (MockController)game.getController();
		controller.insertInput(Integer.toString(fieldPosition));
		action.execute(game.getGameVisor());
		
	}

}
