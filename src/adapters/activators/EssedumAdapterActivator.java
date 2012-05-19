package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Aesculapinum.
 * @author Supalosa
 *
 */
public class EssedumAdapterActivator extends GenericAdapterActivator implements EssedumActivator {


	EssedumParams params;
	
	public EssedumAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		this.params = (EssedumParams) theCard.getParams();
		
	}
	
	@Override
	public void complete() {
		
		execute(params);
		
	}

}
