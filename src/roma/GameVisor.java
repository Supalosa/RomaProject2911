package roma;

import java.util.List;

import modifiers.IModifier;

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
	
	public void onEndTurn (int die1, int die2, int die3) {
		game.nextTurn(); // Increment current player
		game.startTurn(die1, die2, die3); // Perform start of turn actions.
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
	
	/**
	 * Gets this game's action log
	 * @return
	 */
	public ActionLogger getActionLogger() {
		
		return game.getActionLogger();
		
	}

	/**
	 * Copies the state in the specified Game to our own game.
	 * @param updatedGame
	 */
	public void copyStateFrom(Game updatedGame) {
		
		System.out.println ("-- copyStateFrom Start --");
		// VP, Sestertii, 
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {
			game.getPlayer(i).setVP(updatedGame.getPlayer(i).getVP());
			game.getPlayer(i).setMoney(updatedGame.getPlayer(i).getMoney());
			
		}
		
		// Action Dice
		game.setDiceRolls(updatedGame.getDiceRolls().clone());
		
		// Discard pile
		game.getDiscardPile().emptyPile();
		
		for (Card c : updatedGame.getDiscardPile().asList()) {
			
			game.getDiscardPile().addCardToFront(c.getCopy());
			
		}
		
		// Deck pile
		game.getDeck().emptyPile();
		
		for (Card c : updatedGame.getDeck().asList()) {
			
			game.getDeck().addCardToFront(c.getCopy());
			
		}
		
		System.out.println ("-- copyStateFrom End --");
		
		
	}
	
	
	/**
	 * Copies the state in the specified ImmutableGameState to our game
	 * @param updatedGame
	 */
	public void copyStateFrom(ImmutableGameState gameState) {
		
		//System.out.println ("-- copyStateFromImmutable Start --");
		// VP, Sestertii, 
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {
			game.getPlayer(i).setVP(gameState.getVP(i));
			game.getPlayer(i).setMoney(gameState.getSestertii(i));
			
			
			// load hands
			System.out.println("Mutable Hand: " + game.getPlayer(i).getHand());
			game.getPlayer(i).getHand().clear();
			game.getPlayer(i).getHand().addAll(gameState.getHands(i));
			System.out.println("Immutable Hand: " + game.getPlayer(i).getHand());
		}
		
		// Action Dice
		game.setDiceRolls(gameState.getDice());
		
		// Discard pile
		game.getDiscardPile().emptyPile();
				
		for (Card c : gameState.getDiscardPile().asList()) {
			
			game.getDiscardPile().addCard(c.getCopy());
			
		}
		
		// Deck pile
		game.getDeck().emptyPile();
		
		for (Card c : gameState.getDeck().asList()) {
			
			game.getDeck().addCard(c.getCopy());
			
		}
		
		//System.out.println ("-- copyStateFromImmutable End --");
		
		// testing
		try {
			
			assert (game.getDeck().getSize() == gameState.getDeck().getSize());
			assert (game.getDiscardPile().getSize() == gameState.getDiscardPile().getSize());
			
			for (int i = 0; i < Game.MAX_PLAYERS; i++) {
				assert (game.getPlayer(i).getVP() == gameState.getVP(i));
				assert (game.getPlayer(i).getMoney() == gameState.getSestertii(i));
				
			}
			
		} catch (AssertionError ex) {
			
			System.err.println ("Variation when copying immutable state: " + ex.getMessage());
			ex.printStackTrace();
			System.exit(1);
			
		}
	}
	
	
	/**
	 * Called when a time paradox occurs. Ends the game.
	 */
	public void onTimeParadox() {
		
		game.onTimeParadox();
		
	}
	
	/**
	 * Returns the list of modifiers active
	 */
	public List<IModifier> getModifiers() {
		
		return game.getModifiers();

	}
	
	/**
	 * Returns the list of all modifiers casted ON the field position specified
	 */
	public List<IModifier> getModifiersOn(int ownerId, int pos) {
		return game.getModifiersOn(ownerId, pos);
		
	}
	
	
	/**
	 * Returns the list of all modifiers casted ON the field position specified
	 * Wrapper that takes in a card
	 */
	public List<IModifier> getModifiersOn(Card c) {
		
		int ownerId = game.getField().findCardOwner(c);
		int pos = game.getField().findCardPosition(c);
		
		return game.getModifiersOn(ownerId, pos);
		
	}
	
	
	/**
	 *  Returns the list of all modifiers casted BY the field position specified
	 */
	public List<IModifier> getModifiersBy(int ownerId, int pos) {
		return game.getModifiersBy(ownerId, pos);
		
	}
	
	/**
	 * Returns the list of all modifiers casted BY the field position specified
	 * Wrapper that takes in a card
	 */
	public List<IModifier> getModifiersBy(Card c) {
		
		int ownerId = game.getField().findCardOwner(c);
		int pos = game.getField().findCardPosition(c);
		
		return game.getModifiersBy(ownerId, pos);
		
	}
	/**
	 * Adds a modifier to the list
	 */
	public void addModifier(IModifier mod) {
		
		game.addModifier(mod);
		
	}
	
	/**
	 * Deletes the specified modifier
	 */
	public void deleteModifier(IModifier mod) {
		
		game.deleteModifier(mod);
		
	}
	
	/**
	 * Deletes all modifiers casted ON the field position specified
	 */
	public void deleteModifiersOn(int ownerId, int pos) {
		game.deleteModifiersOn(ownerId, pos);
		
	}
	
	/**
	 * Deletes all modifiers casted BY the field position specified
	 */
	public void deleteModifiersBy(int ownerId, int pos) {
		game.deleteModifiersBy(ownerId, pos);
		
	}
	
}
