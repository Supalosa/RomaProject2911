package adapters.activators;

import roma.*;
import cards.Card;
import cards.activators.SicariusParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Legat.
 * @author Supalosa
 *
 */
public class SicariusAdapterActivator extends GenericAdapterActivator implements SicariusActivator {

	SicariusParams params;
	
	public SicariusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		super(fieldPosition, game, theCard);
		
		this.params = (SicariusParams)theCard.getParams();

	}
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	
	@Override
    public void chooseDiceDisc(int diceDisc) {
	
		params.setTargetPos(diceDisc-1);
    
	}

}
