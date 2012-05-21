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
		 */
		isFirstActionMade = false;
	}

	private void checkFirstMove() {

		// first move has been made!
		if (!isFirstActionMade) {

			isFirstActionMade = true;
			game.onGameStarted();
			System.out.println("Game started - logged!");
			Field f = game.getGameStateForTurn(0).getField();
			for (int i = 0; i < Game.FIELD_SIZE; i++) {

				System.out.print(f.getCard(0, i) + " ");

			}

			System.out.println();

		}

	}

	/**
	 * Based on the card in position 'disc', return the appropriate activator
	 * (obviously assumes current player's turn)
	 */
	@Override
	public CardActivator chooseCardToActivate(int disc)
			throws UnsupportedOperationException {

		// Check if it is the first move - if so, save the game state.
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
		int cardIndex = -1;
		int tempIndex;
		// Get the card index (have to guess from the deck before we draw)

		List<cards.Card> deck = game.getDeck().asList();
		/*
		 * if (deck.size() < diceToUse) { game.addDiscardToDeck(); deck =
		 * game.getDeck().asList(); }
		 */

		// stop drawing out if diceToUse > remaining cards
		int overflow = 0;
		if (diceToUse > game.getDeck().getSize()) {
			System.out.println("Warning: outdrawing deck");
			overflow = diceToUse - game.getDeck().getSize();
			diceToUse = game.getDeck().getSize();

		}

		List<cards.Card> topCards = deck.subList(0, diceToUse);

		tempIndex = 0;
		for (cards.Card c : topCards) {

			CardNameAdapter acceptanceAdapter = CardNameAdapter
					.getAcceptanceAdapter(c.getName());
			if (chosen.toString().equals(acceptanceAdapter.getAcceptanceName())) {
				cardIndex = tempIndex;
			}
			tempIndex++;
		}

		if (cardIndex == -1) {

			System.err.println("Error: activatedCardsDisc: could not find "
					+ chosen);
			System.err.println("Drawn cards:");
			for (cards.Card c : topCards) {
				System.err.println("   " + c);
			}
			// System.exit(1);
			assert (false);
		}
		action = new TakeCardAction();
		// mockController.insertInput(Integer.toString(diceToUse));

		// mockController.insertInput(Integer.toString(cardIndex));
		action.setCardIndexTaken(cardIndex);
		action.setDiceRoll(diceToUse);
		action.execute(game.getGameVisor());
		// System.out.println(game.getDiscardPile().asList().size());

		for (int i = 0; i < overflow; i++) {

			cards.Card overdrawn = game.drawCard();
			game.discard(overdrawn);
			System.out.println("overdraw: " + overdrawn);
		}

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
			System.out
					.println("MoveMakerAdapter:activateBribeDisc: Using bribe "
							+ diceToUse + " for " + activatedCard);
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
