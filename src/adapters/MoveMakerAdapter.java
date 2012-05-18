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
		
		// TODO better switching
		if (activatedCard != null) {
			if (activatedCard.getID() == CardNames.Tribunus_Plebis) {
				activator = new TribunusPlebisAdapterActivator(disc, game, activatedCard);
			} else if (activatedCard.getID () == CardNames.Legat){
				activator = new LegatAdapterActivator(disc, game, activatedCard);
			} else if (activatedCard.getID () == CardNames.Sicarius){
				activator = new SicariusAdapterActivator(disc, game, activatedCard);
			} else if (activatedCard.getID () == CardNames.Aesculapinum) {
				activator = new AesculapinumAdapterActivator(disc, game, activatedCard);
			} else if (activatedCard.getID() == CardNames.Consul) {
				activator = new ConsulAdapterActivator(disc, game, activatedCard);
			} else if (activatedCard.getID() == CardNames.Senator) {
				activator = new ConsulAdapterActivator(disc, game, activatedCard);
			} else if (activatedCard.getID() == CardNames.Forum) {
				activator = new ForumAdapterActivator(disc, game, activatedCard);
			} else if (activatedCard.getID() == CardNames.Consiliarius) {
				activator = new ConsiliariusAdapterActivator(disc, game, activatedCard);
			}
		}
		
		return activator;
	}

	@Override
	public void activateCardsDisc(int diceToUse, Card chosen)
			throws UnsupportedOperationException {
		PlayerAction action;
		int cardIndex = 0;
		int tempIndex;
		// Get the card index (have to guess from the deck before we draw)
		
		List<cards.Card> deck = game.getDeck().asList();
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
		mockController.insertInput(Integer.toString(diceToUse));
		
		mockController.insertInput(Integer.toString(cardIndex));
		
		action.execute(game.getGameVisor());

	}

	@Override
	public void activateMoneyDisc(int diceToUse)
			throws UnsupportedOperationException {
		
		PlayerAction action = new TakeMoneyAction();
		mockController.insertInput(Integer.toString(diceToUse));
		action.execute(game.getGameVisor());

	}

	@Override
	public CardActivator activateBribeDisc(int diceToUse)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * End the turn
	 */
	@Override
	public void endTurn() throws UnsupportedOperationException {
		PlayerAction action = new EndTurnAction();
		mockController.insertInput("Y");
		action.execute(game.getGameVisor());
		
	}

	/**
	 * Place a card
	 */
	@Override
	public void placeCard(Card toPlace, int discToPlaceOn)
			throws UnsupportedOperationException {
		
		PlayerAction action = new PlayCardAction();
		
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
		mockController.insertInput(Integer.toString(handIndex));
		mockController.insertInput(Integer.toString(discToPlaceOn));
		action.execute(game.getGameVisor());

	}

}
