package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.MercatusParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Mercatus.
 * @author Supalosa
 *
 */
public class MercatusAdapterActivator extends GenericAdapterActivator implements MercatusActivator {

	MercatusParams params;
	
	public MercatusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		params = (MercatusParams)theCard.getParams();
	}
	
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	

}
