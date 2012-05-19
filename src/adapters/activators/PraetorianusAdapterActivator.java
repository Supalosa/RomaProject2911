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
public class PraetorianusAdapterActivator extends GenericAdapterActivator implements PraetorianusActivator {


	PraetorianusParams params;
	
	public PraetorianusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		this.params = (PraetorianusParams) theCard.getParams();
		
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
