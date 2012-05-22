package adapters.activators;

import roma.*;
import cards.Card;
import cards.activators.NeroParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Nero.
 * @author Supalosa
 *
 */
public class NeroAdapterActivator extends GenericAdapterActivator implements NeroActivator {

	NeroParams params;
	
	public NeroAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		super(fieldPosition, game, theCard);
		
		this.params = (NeroParams)theCard.getParams();

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
