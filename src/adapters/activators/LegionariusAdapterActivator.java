package adapters.activators;

import roma.*;
import cards.Card;
import cards.activators.*;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Legionarius.
 * @author Supalosa
 *
 */
public class LegionariusAdapterActivator extends GenericAdapterActivator implements LegionariusActivator {

	LegionariusParams params;
	
	public LegionariusAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		super(fieldPosition, game, theCard);
		
		this.params = (LegionariusParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	



	@Override
	public void giveAttackDieRoll(int roll) {
		
		params.setBattleDie(roll);
	
	}

}
