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
public class TribunusPlebisAdapterActivator implements TribunusPlebisActivator {

	Card theCard;
	int fieldPosition;
	Game game;
	
	public TribunusPlebisAdapterActivator(int fieldPosition, Game game, Card theCard) {
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
	}
	
	
	
	@Override
	public void complete() {
		IPlayerAction action = new ActivateCardAction(null);
		MockController controller = (MockController)game.getController();
		controller.insertInput(Integer.toString(fieldPosition));
		action.execute(game.getGameVisor());
	}

}
