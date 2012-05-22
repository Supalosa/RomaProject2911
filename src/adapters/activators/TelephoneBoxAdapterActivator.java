package adapters.activators;

import roma.*;
import cards.Card;
import cards.activators.TelephoneBoxParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Telephone Box (spew).
 * @author Supalosa
 *
 */
public class TelephoneBoxAdapterActivator extends GenericAdapterActivator implements TelephoneBoxActivator {

	TelephoneBoxParams params;

	public TelephoneBoxAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		super(fieldPosition, game, theCard);
		
		this.params = new TelephoneBoxParams();
		
	}
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	
	@Override
	public void chooseDiceDisc(int diceDisc) {

		params.setDiceToSend(diceDisc);
	
	}

	@Override
	public void shouldMoveForwardInTime(boolean isForward) {
	
		params.setGoForward(isForward);
	
	}

	@Override
	public void setSecondDiceUsed(int diceValue) {
	
		params.setDiceToUse(diceValue);
	
	}

}
