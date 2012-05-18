package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import framework.interfaces.activators.*;
import cards.activators.*;
/**
 * Adapter to activate the Legat.
 * @author Supalosa
 *
 */
public class LegatAdapterActivator implements LegatActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	LegatParams params;
	
	public LegatAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.params = new LegatParams();
	}
	
	
	
	@Override
	public void complete() {
		
		IPlayerAction action = new ActivateCardAction(params);
		MockController controller = (MockController)game.getController();
		controller.insertInput(Integer.toString(fieldPosition));
		action.execute(game.getGameVisor());
		
	}

}
