package adapters;

import java.util.*;

import roma.*;
import framework.cards.Card;
import framework.interfaces.*;

public class GameAdapter implements GameState {
	
	private IController myController;
	private Game myGame;
	
	public GameAdapter() {
	
		myController = new MockController();
		myGame = new Game(myController);
		myController.setGame (myGame);
	
	}
	
	public Game getGame () {
	
		return myGame;
	
	}

	public IController getController() {
	
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

	/**
	 * Convert our deck to Acceptance format (list of enums)
	 */
	@Override
	public List<Card> getDeck() {

		List<cards.Card> oldList = myGame.getDeck().asList();
		List<framework.cards.Card> newList = new ArrayList<framework.cards.Card>();
		
		for (cards.Card c : oldList) {
			// convert to acceptance
			CardNameAdapter acceptanceName = CardNameAdapter.getAcceptanceAdapter(c.getName());
			
			if (acceptanceName == null) {
			
				System.err.println ("GameAcceptanceAdapter::getDeck: Could not convert "
									+ c.getName() + " to Acceptance equivalent");
			
			} else {
			
				newList.add(Card.valueOf(acceptanceName.getAcceptanceName().toUpperCase()));
			
			}
		
		}
		
		return newList;
	
	}
	
	/**
	 * Convert the provided list to a list of our own cards,
	 * then empty the discard pile and insert
	 * 
	 */

	@Override
	public void setDeck(List<Card> discard) {
		
		CardTypes cardList = new CardTypes();
		myGame.getDeck().emptyPile();
		
		for (Card c : discard) {
			// convert to roma
			CardNameAdapter romaName = CardNameAdapter.getRomaAdapter(c.toString());
		
			if (romaName == null) {
			
				System.err.println ("GameAcceptanceAdapter::setDeck: Could not convert "
									+ c.name() + " to Roma equivalent");
			
			} else {
			
				cards.Card romaCard = cardList.getCard(romaName.getRomaEnum());
				myGame.getDeck().addCard(romaCard);
		
			}
		
		}
		
	}

	/**
	 * Convert our discard pile to Acceptance format (list of enums)
	 */
	@Override
	public List<Card> getDiscard() {

		List<cards.Card> oldList = myGame.getDiscardPile().asList();
		List<framework.cards.Card> newList = new ArrayList<framework.cards.Card>();
		
		for (cards.Card c : oldList) {
			// convert to acceptance
			CardNameAdapter acceptanceName = CardNameAdapter.getAcceptanceAdapter(c.getName());
			
			if (acceptanceName == null) {
			
				System.err.println ("GameAcceptanceAdapter::getDiscard: Could not convert "
									+ c.getName() + " to Acceptance equivalent");
			
			} else {
			
				newList.add(Card.valueOf(acceptanceName.getAcceptanceName().toUpperCase()));
			
			}
		
		}
		
		return newList;
	
	}
	
	/**
	 * Convert the provided list to a list of our own cards,
	 * then empty the discard pile and insert
	 * 
	 */

	@Override
	public void setDiscard(List<Card> discard) {
		
		CardTypes cardList = new CardTypes();
		myGame.getDiscardPile().emptyPile();
		
		for (Card c : discard) {
			// convert to roma
			CardNameAdapter romaName = CardNameAdapter.getRomaAdapter(c.toString());
		
			if (romaName == null) {
			
				System.err.println ("GameAcceptanceAdapter::setDiscard: Could not convert "
									+ c.name() + " to Roma equivalent");
			
			} else {
				
				cards.Card romaCard = cardList.getCard(romaName.getRomaEnum());
				myGame.getDiscardPile().addCard(romaCard);
			
			}
		
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

	/**
	 * Convert the player's hand into a list of Acceptance card names
	 */
	@Override
	public Collection<Card> getPlayerHand(int playerNum) {
	
		List<Card> playerHand = new ArrayList<Card>();
		
		for (cards.Card c : myGame.getPlayer(playerNum).getHand()) {
			// convert to acceptance
			CardNameAdapter acceptanceName = CardNameAdapter.getAcceptanceAdapter(c.getName());
			
			playerHand.add(Card.valueOf(acceptanceName.getAcceptanceName().toUpperCase()));
		
		}
		
		return playerHand;
	
	}

	/**
	 * Convert the provided list to a list of our own cards,
	 * then empty the hand
	 * 
	 */
	@Override
	public void setPlayerHand(int playerNum, Collection<Card> hand) {
		
		CardTypes cardList = new CardTypes();
		myGame.getPlayer(playerNum).getHand().clear();
		
		for (Card c : hand) {
			// convert to roma
			CardNameAdapter romaName = CardNameAdapter.getRomaAdapter(c.toString());
			
			if (romaName == null) {
				
				System.err.println ("GameAcceptanceAdapter::setPlayerHand: Could not convert "
									+ c.name() + " to Roma equivalent");
			
			} else {
				
				cards.Card romaCard = cardList.getCard(romaName.getRomaEnum());
				myGame.getPlayer(playerNum).addCard(romaCard);
				
			}
			
		}
		
	}
	


	@Override
	public Card[] getPlayerCardsOnDiscs(int playerNum) {
		
		Card[] fieldCards = new Card[Game.FIELD_SIZE];
		
		for (int i = 0; i < Game.FIELD_SIZE; i++) {
		
			cards.Card cardInPosition = myGame.getField().getCard(playerNum,i);
			
			if (cardInPosition == null) {
			
				fieldCards[i] = Card.NOT_A_CARD;
			
			} else {
			
				CardNameAdapter acceptanceName = CardNameAdapter.getAcceptanceAdapter(cardInPosition.getName());
				fieldCards[i] = Card.valueOf(acceptanceName.getAcceptanceName().toUpperCase());
			
			}
		
		}
	
		return fieldCards;
	
	}

	@Override
	public void setPlayerCardsOnDiscs(int playerNum, Card[] discCards) {
		
		CardTypes cardList = new CardTypes();
		
		for (int i = 0; i < Game.FIELD_SIZE; i++) {
			// convert to roma format
			cards.Card thisCard = null;
		
			if (!discCards[i].equals(Card.NOT_A_CARD)) {
			
				CardNameAdapter romaName = CardNameAdapter.getRomaAdapter(discCards[i].toString());
				thisCard = cardList.getCard(romaName.getRomaEnum());
			
			}
			
			myGame.getField().setCard(playerNum,i,thisCard);
		
		}
		
	}

	/**
	 * Return the number of die that have not been used
	 */
	@Override
	public int[] getActionDice() {
		
		int numDie = 0;
		List<Integer> rolls = new ArrayList<Integer>();
		int [] result;
		
		for (int i = 0; i < Game.NUM_DIE; i++) {
		
			if (myGame.getDiceRoll(i) > 0) {
			
				rolls.add(myGame.getDiceRoll(i));
			
			}
		
		}
		
		result = new int[rolls.size()];
		
		for (Integer a : rolls) {
		
			result[numDie++] = a.intValue();
		
		}
		
		return result;
	
	}

	@Override
	public void setActionDice(int[] dice) {
		
		int []diceRolls = Arrays.copyOf(dice, Game.NUM_DIE);
		
		myGame.setDiceRolls(diceRolls);
		
	}

	@Override
	public int getPoolVictoryPoints() {
	
		return myGame.getVictoryStockpile();
	
	}

	@Override
	public boolean isGameCompleted() {
		
		return myGame.testGameOver();
	
	}

}
