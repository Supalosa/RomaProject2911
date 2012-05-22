package adapters.activators;

import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Centurio.
 * @author Supalosa
 *
 */
public class CenturioAdapterActivator extends GenericAdapterActivator implements CenturioActivator {

	CenturioParams params;
	
	public CenturioAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);
		
		this.params = (CenturioParams) theCard.getParams();
		
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
	public void chooseActionDice(int actionDiceValue) {
		params.setExtraDieValue(actionDiceValue);
	}



	@Override
	public void chooseCenturioAddActionDie(boolean attackAgain) {
		params.setUseExtraDice(attackAgain);
	}

}
