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
 * based on functioned called from MoveMaker, sends a Controller input to the Game.
 * @author Supalosa
 *
 */
public class MoveMakerAdapter implements MoveMaker {

	boolean endTurn;
	MockController mockController;
	Game game;
	GameAdapter adapter;
	
	public MoveMakerAdapter(GameAdapter ga, IController mockController) {
		endTurn = false;
		this.mockController = (MockController)mockController;
		this.adapter = ga;
		this.game = ga.getGame();
	}
	
	/**
	 * Based on the card in position 'disc', return the appropriate activator
	 * (obviously assumes current player's turn)
	 */
	@Override
	public CardActivator chooseCardToActivate(int disc)
			throws UnsupportedOperationException {
		
		CardActivator activator = null;
		cards.Card activatedCard = game.getField().getCard(game.whoseTurn(), disc-1);

		if (activatedCard != null) {
			activator = CardActivatorAdapter.getActivator(activatedCard.getID(), disc, game, activatedCard);
		}
		
		return activator;
	}

	@Override
	public void activateCardsDisc(int diceToUse, Card chosen)
			throws UnsupportedOperationException {
		TakeCardAction action;
		int cardIndex = 0;
		int tempIndex;
		// Get the card index (have to guess from the deck before we draw)
		
		List<cards.Card> deck = game.getDeck().asList();
		if (deck.size() < diceToUse) {
			game.addDiscardtoDeck();
			deck = game.getDeck().asList();
		}
		List<cards.Card> topCards = deck.subList(0, diceToUse);
		
		tempIndex = 0;
		for (cards.Card c : topCards) {
			
			CardNameAdapter acceptanceAdapter = CardNameAdapter.getAcceptanceAdapter(c.getName());
			if (chosen.toString().equals(acceptanceAdapter.getAcceptanceName())) {
				cardIndex = tempIndex;
			}
			tempIndex ++;
		}
		
		action =  new TakeCardAction();
		//mockController.insertInput(Integer.toString(diceToUse));
		
		//mockController.insertInput(Integer.toString(cardIndex));
		action.setCardIndexTaken(cardIndex);
		action.setDiceRoll(diceToUse);
		action.execute(game.getGameVisor());
		System.out.println(game.getDiscardPile().asList().size());

	}

	@Override
	public void activateMoneyDisc(int diceToUse)
			throws UnsupportedOperationException {
		
		TakeMoneyAction action = new TakeMoneyAction();
		//mockController.insertInput(Integer.toString(diceToUse));
		action.setDiceToUse(diceToUse);
		action.execute(game.getGameVisor());

	}

	@Override
	public CardActivator activateBribeDisc(int diceToUse)
			throws UnsupportedOperationException {
		GenericAdapterActivator activator = null;
		cards.Card activatedCard = game.getField().getCard(game.whoseTurn(), Game.BRIBE_DISC-1);

		if (activatedCard != null) {
			activator = (GenericAdapterActivator) CardActivatorAdapter.getActivator(activatedCard.getID(), Game.BRIBE_DISC, game, activatedCard);
			System.out.println("MoveMakerAdapter:activateBribeDisc: Using bribe " + diceToUse + " for " + activatedCard);
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
		//mockController.insertInput("Y");
		action.setEndTurn(true);
		action.execute(game.getGameVisor());
		
	}

	/**
	 * Place a card
	 */
	@Override
	public void placeCard(Card toPlace, int discToPlaceOn)
			throws UnsupportedOperationException {
		
		PlayCardAction action = new PlayCardAction();
		
		// Have to determine which card corresponds to toPlace
		int handIndex = -1;
		int tempIndex = 0;
		for (cards.Card c : game.getPlayer(game.whoseTurn()).getHand()) {
			CardNameAdapter romaAdapter = CardNameAdapter.getAcceptanceAdapter(c.getName());
			if (romaAdapter.getAcceptanceName() == toPlace.toString()) {
				handIndex =  tempIndex;
			}
			
			tempIndex ++;			
		}
		//mockController.insertInput(Integer.toString(handIndex));
		//mockController.insertInput(Integer.toString(discToPlaceOn));
		action.setTargetHandCard(handIndex);
		action.setTargetDisc(discToPlaceOn);
		action.execute(game.getGameVisor());

	}

}
