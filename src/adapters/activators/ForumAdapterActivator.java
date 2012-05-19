package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.ForumParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Forum.
 * @author Supalosa
 *
 */
public class ForumAdapterActivator extends GenericAdapterActivator implements ForumActivator {

	ForumParams params;
	
	
	public ForumAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		this.params = new ForumParams();

		
	}
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	

	@Override
	public void chooseActionDice(int actionDiceValue) {
		params.setForumDie(actionDiceValue);
	}



	@Override
	public void chooseActivateTemplum(boolean activate) {
		params.setUseTemplum(activate);
	}



	@Override
	public void chooseActivateTemplum(int diceValue) {
		//params.setUseTemplum(activate);
		
	}


}
