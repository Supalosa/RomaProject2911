package roma;

import java.util.*;
import framework.cards.Card;
import framework.interfaces.*;

public class GameAcceptanceAdapter implements GameState {
	private MoveMakerAdapter myController;
	private Game myGame;
	
	public GameAcceptanceAdapter() {
		myController = new MoveMakerAdapter();
		myGame = new Game(myController);
		myController.setGame (myGame);
	}

	public MoveMakerAdapter getMoveMaker() {
		return myController;
	}
	
	@Override
	public int getWhoseTurn() {
		return myGame.whoseTurn();
	}

	@Override
	public void setWhoseTurn(int player) {
		myGame.setWhoseTurn(player);
	}

	@Override
	/**
	 * converts our representation of Card to framework's Card
	 */
	public List<Card> getDeck() {
		List<cards.Card> oldList = myGame.getDeck().asList();
		List<framework.cards.Card> newList = new ArrayList<framework.cards.Card>();
		for (cards.Card c : oldList) {
			newList.add(framework.cards.Card.valueOf(c.getName()));
		}
		return newList;
	}

	@Override
	public void setDeck(List<Card> deck) {
		CardTypes cardList = new CardTypes();
		cards.Card[] field = new cards.Card[Game.FIELD_SIZE];
		for (Card c : deck) {
			myGame.getDeck().addCard(cardList.getCardFromString(c.toString()));
		}
	}

	@Override
	public List<Card> getDiscard() {

		List<cards.Card> oldList = myGame.getDiscardPile().asList();
		List<framework.cards.Card> newList = new ArrayList<framework.cards.Card>();
		
		for (cards.Card c : oldList) {
			newList.add(framework.cards.Card.valueOf(c.getName()));
		}
		
		return newList;
	}

	@Override
	public void setDiscard(List<Card> discard) {
		CardTypes cardList = new CardTypes();
		cards.Card[] field = new cards.Card[Game.FIELD_SIZE];
		for (Card c : discard) {
			myGame.getDiscardPile().addCard(cardList.getCardFromString(c.toString()));
		}
	}

	@Override
	public int getPlayerSestertii(int playerNum) {
		return myGame.getPlayer(playerNum).getMoney();
	}

	@Override
	public void setPlayerSestertii(int playerNum, int amount) {
		myGame.getPlayer(playerNum).setMoney(amount);
	}

	@Override
	public int getPlayerVictoryPoints(int playerNum) {
		return myGame.getPlayer(playerNum).getVP();

	}

	@Override
	public void setPlayerVictoryPoints(int playerNum, int points) {
		myGame.getPlayer(playerNum).setVP(points);
	}

	@Override
	public Collection<Card> getPlayerHand(int playerNum) {
		List<Card> playerHand = new ArrayList<Card>();
		for (cards.Card c : myGame.getPlayer(playerNum).getHand()) {
			playerHand.add(Card.valueOf(c.getName()));
		}
		return playerHand;
	}

	@Override
	public void setPlayerHand(int playerNum, Collection<Card> hand) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card[] getPlayerCardsOnDiscs(int playerNum) {
		Card[] fieldCards = new Card[Game.FIELD_SIZE];
		for (int i = 0; i < Game.FIELD_SIZE; i++) {
			cards.Card cardInPosition = myGame.getField().getCard(playerNum,i);
			if (cardInPosition == null) {
				fieldCards[i] = Card.NOT_A_CARD;
			} else {
				fieldCards[i] = Card.valueOf(cardInPosition.getName());
			}
		}
		return fieldCards;
	}

	@Override
	public void setPlayerCardsOnDiscs(int playerNum, Card[] discCards) {
		CardTypes cardList = new CardTypes();
		cards.Card[] field = new cards.Card[Game.FIELD_SIZE];
		for (int i = 0; i < Game.FIELD_SIZE; i++) {
			cards.Card thisCard = cardList.getCardFromString(discCards.toString());
			myGame.getField().setCard(playerNum,i,thisCard);
		}
		
		
	}

	@Override
	public int[] getActionDice() {
		return myGame.getDiceRolls();
	}

	@Override
	public void setActionDice(int[] dice) {
		for (int i = 0; i < dice.length; i++) {
			myGame.getDiceRolls()[i] = dice[i];
		}
	}

	@Override
	public int getPoolVictoryPoints() {
		return myGame.getVictoryStockpile();
	}
	
	
	

}
