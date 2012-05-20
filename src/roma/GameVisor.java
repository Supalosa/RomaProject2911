package roma;

import cards.Card;

public class GameVisor {
	private Game game;
	
	public GameVisor (Game g) {
		game = g;
	}
	
	public Field getField() {
		
		return game.getField();
		
	}
	
	public Player getCurrentPlayer () {
		
		return game.getCurrentPlayer();
	
	}
	
	public int whoseTurn() {
		
		return game.whoseTurn();
		
	}
	
	public int[] getDiceRolls() {
		
		return game.getDiceRolls();
		
	}
	
	public void useDice (int value) {
		
		game.useDice(value);
	
	}
	
	public int getDiceRoll(int i) {
		return game.getDiceRoll(i);
	}
	
	public boolean hasDiceRoll (int roll) {
		return game.hasDiceRoll(roll);
	}
	
	public int getNumDiceRolls() {
		return game.getNumDiceRolls();
	}
	
	public void setDiceRoll(int oldValue, int newValue) {
		game.setDiceRoll(oldValue, newValue);
	}
		
	public IController getController() {
		
		return game.getController();
		
	}
	
	public void discard(Card c) {
		
		game.discard (c);
		
	}
	
	public void addCard(int targetPlayer, Card c) {
		game.getPlayer(targetPlayer).addCard(c);
	}
	
	public void giveCard(int currentPlayer, int targetPlayer, Card c) {
		
		game.giveCard(currentPlayer, targetPlayer, c);
		
	}
	
	public void onEndTurn () {
		game.nextTurn(); // Increment current player
		game.startTurn(); // Perform start of turn actions.
	}
	
	public Player getPlayer (int player) {
		
		return game.getPlayer(player);
	
	}
	
	public Card drawCard() {
		
		return game.drawCard();
	
	}
	
	public Pile getDiscardPile() {
		
		return game.getDiscardPile();
		
	}
	
	// Get the deck
	public Pile getDeck () {
		
		return game.getDeck();
	}
	
	public int rollDice() {
		
		return game.rollDice();
		
	}
	

	// Used by: Aescilapinum
	public boolean isDiscardPileEmpty() {
		return game.getDiscardPile().isEmpty();
	}
	
	public int getTurnNumber() {
		
		return game.getTurnNumber();
		
	}
	
	/**
	 * Add the specified TimeTravellingCard to the collection.
	 * Used by: Grim Reaper
	 * @param tc
	 */
	public void addPendingFutureCard(TimeTravellingCard tc) {
		
		game.addPendingFutureCard(tc);
		
	}
	
}
