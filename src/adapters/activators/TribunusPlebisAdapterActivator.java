package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.TribunusPlebisParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the TribunusPlebis.
 * @author Supalosa
 *
 */
public class TribunusPlebisAdapterActivator extends GenericAdapterActivator implements TribunusPlebisActivator {

	TribunusPlebisParams params;
	
	public TribunusPlebisAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		this.params = (TribunusPlebisParams)theCard.getParams();

	}
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}

}
