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
public class OnagerAdapterActivator extends GenericAdapterActivator implements OnagerActivator {

	OnagerParams params;
	
	public OnagerAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		this.params = (OnagerParams) theCard.getParams();
		
	}
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}



	@Override
	public void giveAttackDieRoll(int roll) {
		params.setBattleDie(roll);
	}



	@Override
	public void chooseDiceDisc(int diceDisc) {
		params.setPositionToAttack(diceDisc-1);
		
	}
}
