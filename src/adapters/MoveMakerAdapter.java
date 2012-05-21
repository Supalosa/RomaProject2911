package adapters;

import java.util.*;

import roma.*;

import actions.*;
import adapters.activators.*;
import enums.CardNames;
import framework.cards.Card;
import framework.interfaces.MoveMaker;
import framework.interfaces.activators.CardActivator;

/**
 * based on functioned called from MoveMaker, sends a Controller input to the
 * Game.
 * 
 * @author Supalosa
 * 
 */
public class MoveMakerAdapter implements MoveMaker {

	private boolean endTurn;
	private MockController mockController;
	private Game game;
	private GameAdapter adapter;
	private boolean isFirstActionMade; // hacky

	public MoveMakerAdapter(GameAdapter ga, IController mockController) {
		endTurn = false;
		this.mockController = (MockController) mockController;
		this.adapter = ga;
		this.game = ga.getGame();

		/**
		 * This variable is needed because the acceptance testing does not have
		 * a clear definition of when the first turn starts (i.e., after all the
		 * 'setters' have finished setting)
		 * 
		 * So instead, we declare the game to have started when the first action
		 * is made.
		 * 
		 * This is necessary because Telephone Box requires the ability to
		 * revert to Turn 0, which is not clearly delineated in Acceptance
		 * Testing mode - the setters run, then the actions (in MoveMaker) are
		 * called.
		 * 
		 * 
		 * NOTE: as of 6:15am the day this shit is due, I decided to make it
		 * check firstaction every turn.. first move after EndTurnAction
		 * 
		 * 
		 */
		isFirstActionMade = false;
	}

	private void checkFirstMove() {

		// first move has been made!
		if (!isFirstActionMade) {

			isFirstActionMade = true;
			game.onGameStarted();
			game.takeSnapshot();
			//System.out.println("Game started - logged!");
			

		}

	}

	/**
	 * Based on the card in position 'disc', return the appropriate activator
	 * (obviously assumes current player's turn)
	 */
	@Override
	public CardActivator chooseCardToActivate(int disc)
			throws UnsupportedOperationException {

		// Check if it is the first move after a turn ended - if so, save the
		// game state.
		checkFirstMove();

		CardActivator activator = null;
		cards.Card activatedCard = game.getField().getCard(game.whoseTurn(),
				disc - 1);

		if (activatedCard != null) {
			activator = CardActivatorAdapter.getActivator(
					activatedCard.getID(), disc, game, activatedCard);
		}

		return activator;
	}

	@Override
	public void activateCardsDisc(int diceToUse, Card chosen)
			throws UnsupportedOperationException {

		// Check if it is the first move - if so, save the game state.
		checkFirstMove();

		TakeCardAction action;

		// Convert the chosen to internal ID
		CardNameAdapter romaCard = CardNameAdapter.getRomaAdapter(chosen);
		CardNames requestedCard = romaCard.getRomaEnum();

		action = new TakeCardAction();

		action.setCardIndexTaken(requestedCard);
		action.setDiceRoll(diceToUse);
		action.execute(game.getGameVisor());

	}

	@Override
	public void activateMoneyDisc(int diceToUse)
			throws UnsupportedOperationException {

		// Check if it is the first move - if so, save the game state.
		checkFirstMove();

		TakeMoneyAction action = new TakeMoneyAction();
		// mockController.insertInput(Integer.toString(diceToUse));
		action.setDiceToUse(diceToUse);
		action.execute(game.getGameVisor());

	}

	@Override
	public CardActivator activateBribeDisc(int diceToUse)
			throws UnsupportedOperationException {

		// Check if it is the first move - if so, save the game state.
		checkFirstMove();

		GenericAdapterActivator activator = null;
		cards.Card activatedCard = game.getField().getCard(game.whoseTurn(),
				Game.BRIBE_DISC - 1);

		if (activatedCard != null) {
			activator = (GenericAdapterActivator) CardActivatorAdapter
					.getActivator(activatedCard.getID(), Game.BRIBE_DISC, game,
							activatedCard);
			/*System.out
					.println("MoveMakerAdapter:activateBribeDisc: Using bribe "
							+ diceToUse + " for " + activatedCard);*/
			activator.setBribe(diceToUse);
		}

		return activator;
	}

	/**
	 * End the turn
	 */
	@Override
	public void endTurn() throws UnsupportedOperationException {
		EndTurnAction action = new EndTurnAction();

		// Check if it is the first move - if so, save the game state.
		checkFirstMove();

		// mockController.insertInput("Y");
		action.setEndTurn(true);
		action.execute(game.getGameVisor());
		
		// listen for first action
		isFirstActionMade = false;

	}

	/**
	 * Place a card
	 */
	@Override
	public void placeCard(Card toPlace, int discToPlaceOn)
			throws UnsupportedOperationException {

		// Check if it is the first move - if so, save the game state.
		checkFirstMove();

		PlayCardAction action = new PlayCardAction();

		// Have to determine which card corresponds to toPlace
		int handIndex = -1;
		int tempIndex = 0;
		for (cards.Card c : game.getPlayer(game.whoseTurn()).getHand()) {
			CardNameAdapter romaAdapter = CardNameAdapter
					.getAcceptanceAdapter(c.getName());
			if (romaAdapter.getAcceptanceName() == toPlace.toString()) {
				handIndex = tempIndex;
			}

			tempIndex++;
		}
		// mockController.insertInput(Integer.toString(handIndex));
		// mockController.insertInput(Integer.toString(discToPlaceOn));
		action.setTargetHandCard(handIndex);
		action.setTargetDisc(discToPlaceOn);
		action.execute(game.getGameVisor());

	}

}
