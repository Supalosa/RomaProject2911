package adapters.activators;

import roma.*;
import cards.Card;
import framework.interfaces.activators.*;
import cards.activators.*;
/**
 * Adapter to activate the Legat.
 * @author Supalosa
 *
 */
public class LegatAdapterActivator extends GenericAdapterActivator implements LegatActivator {

	LegatParams params;
	
	public LegatAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		super(fieldPosition, game, theCard);
		
		this.params = new LegatParams();
	
	}
	
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	

}
