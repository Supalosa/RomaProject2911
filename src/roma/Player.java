package roma;

import cards.Card;

public class Player {
	
	private CardVector hand; // Unbounded list of cards (see rules P3, player may have as many cards as they want)
	private int money; // sesterii
	private int victoryPoints = 10; // my victory points
	private int playerId; // possibly redundant, 0..1

	public static final int NO_OWNER = -1;
	// Constructor for Player
	public Player(int id) {
		
		playerId = id;
		hand = new CardVector();
		
	}

	public int getPlayerId () {
		
		return playerId;
	
	}
	
	// Add a card to our hand
	public void addCard(Card c) {
		
		c.onEnterHand(this);
		c.setOwnerId(playerId);
		hand.add(c);
		
	}
	
	public void removeCard(Card c) {
		
		c.onLeaveHand(this);
		c.setOwnerId(NO_OWNER);
		hand.remove(c);
	
	}

	// Get n'th card from my hand
	public Card getCard(int n) {
		
		return hand.get(n);
		
	}

	// Get hand size
	public int getHandSize() {
	
		return hand.size();
	
	}
	
	public CardVector getHand() {
		
		return hand;
		
	}
	
	public int getVP() {
		
		return victoryPoints;
		
	}
	
	public int getMoney() {
		
		return money;
		
	}
	
	public void setVP(int n) {
		
		victoryPoints = n;
		
	}
	
	public void setMoney(int n) {
		
		money = n;
		
	}
	
	
}