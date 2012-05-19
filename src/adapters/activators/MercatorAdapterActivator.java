package adapters.activators;

import actions.*;
import roma.*;
import cards.Card;
import cards.activators.MercatorParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Mercator.
 * @author Supalosa
 *
 */
public class MercatorAdapterActivator extends GenericAdapterActivator implements MercatorActivator {


	MercatorParams params;
	
	public MercatorAdapterActivator(int fieldPosition, Game game, Card theCard) {
		super(fieldPosition, game, theCard);

		this.params = (MercatorParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		
		execute(params);
		
	}
	



	@Override
	public void chooseMercatorBuyNum(int VPToBuy) {
		params.setMoneyToSpend(VPToBuy*2);
	}




}
