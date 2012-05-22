package adapters.activators;

import roma.*;
import cards.*;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the TribunusPlebis.
 * @author Supalosa
 *
 */
public class ConsulAdapterActivator extends GenericAdapterActivator implements ConsulActivator {


	ConsulParams params;
	
	public ConsulAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		super(fieldPosition, game, theCard);
		
		this.params = new ConsulParams();
		
	}
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}

	/**
	 * We send Y if increasing value, N if decreasing
	 */
	@Override
	public void chooseConsulChangeAmount(int amount) {
		
		if (amount > 0) {
		
			params.setIncreaseDice(true);
		
		} else {
		
			params.setIncreaseDice(false);
		
		}
	
	}


	
	@Override
	public void chooseWhichDiceChanges(int originalRoll) {
	
		params.setDiceValue(originalRoll);
	
	}

}
