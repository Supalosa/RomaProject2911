package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Onager.
 * @author Supalosa
 *
 */
public class GladiatorAdapterActivator extends GenericAdapterActivator implements GladiatorActivator {


	GladiatorParams params;
	
	public GladiatorAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		this.params = (GladiatorParams) theCard.getParams();
		
	}
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	

	@Override
	public void chooseDiceDisc(int diceDisc) {
		params.setPositionToAttack(diceDisc-1);
		
	}
}
