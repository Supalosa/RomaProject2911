package adapters.activators;

import java.util.*;

import actions.*;
import adapters.CardNameAdapter;
import roma.*;
import cards.Card;
import cards.activators.ConsiliariusParams;
import cards.activators.MercatorParams;
import framework.interfaces.activators.*;

/**
 * Adapter to activate the Mercator.
 * @author Supalosa
 *
 */
public class MercatorAdapterActivator implements MercatorActivator {

	Card theCard;
	int fieldPosition;
	Game game;

	MercatorParams params;
	
	public MercatorAdapterActivator(int fieldPosition, Game game, Card theCard) {
		
		this.theCard = theCard;
		this.fieldPosition = fieldPosition;
		this.game = game;
		this.params = (MercatorParams) theCard.getParams();
		
	}
	
	
	
	@Override
	public void complete() {
		IPlayerAction action = new ActivateCardAction(params);
		MockController controller = (MockController)game.getController();

		controller.insertInput(Integer.toString(fieldPosition));
		

		action.execute(game.getGameVisor());
	}



	@Override
	public void chooseMercatorBuyNum(int VPToBuy) {
		params.setMoneyToSpend(VPToBuy*2);
	}




}
