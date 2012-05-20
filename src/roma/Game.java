package roma;
import java.text.*;
import java.util.*;

import cards.*;
import actions.*;


public class Game {
	
	private Player[] players; // 2 players
	private Pile deck; // stack of cards
	private Field field; // 12 cards, 0-5 for p1, 6-11 for p2
	private Pile discardPile; // stack of cards
	private int[] diceRolls; // 3 dice rolls
	private int currentPlayer; // id 0..1 of current turn's player
	private RandomGenerator generator;
	private CardTypes cardTypes;
	private IController controller;
	private boolean gameOver;
	private List<Card> [] swappedCards;
	
	private int turnNumber;
	
	// Maps of Card -> Position for future cards to be placed
	private List<TimeTravellingCard> pendingFutureCards; 
	
	// log of actions
	private ActionLogger logger;
	
	
	private GameVisor visor;
	
	public static final int FIELD_SIZE = 7;
	public static final int BRIBE_DISC = 7; // field position of bribe disc
	public static final int MAX_PLAYERS = 2;
	public static final int INITIAL_VP = 36;
	public static final int NUM_DIE = 3;
	
	public static final int INITIAL_CARDS = 5; 
	public static final int NUM_SWAP_CARDS = 2; // number of cards to swap at start of game
	
	public Game(IController controller) {
	
		generator = new RandomGenerator();
		this.controller = controller;
		gameOver = false;
		visor = new GameVisor(this);
		initGame();
		
	}
	
	/**
	 * Initialise the game pointers, etc.
	 */
	@SuppressWarnings("unchecked")
	public void initGame() {
		
		currentPlayer = 0;
		cardTypes = new CardTypes();

		players = new Player[MAX_PLAYERS];
		for (int i = 0; i < MAX_PLAYERS; i++) {
			players[i] = new Player(i);
		}
			
		deck = new Deck();

		
		field = new Field(this);
		discardPile = new DiscardPile();
		
		diceRolls = new int[NUM_DIE];
		
		swappedCards = new List[MAX_PLAYERS];
		
		turnNumber = 0;
		
		pendingFutureCards = new ArrayList<TimeTravellingCard>();
		
		logger = new ActionLogger();
	}
	
	/**
	 * Automated loop that runs the game.
	 * Is only used when playing, NOT when testing.
	 */
	public void run() {
		// small change 2
	/* this is f-ing annoying
		plr1 = getBirthday(1);
		plr2 = getBirthday(2);
		
		if (plr1.compareTo(plr2) > 0) { // player 1 is younger
			System.out.println("Player 1 is younger.");
			currentPlayer = 0;
		} else {
			System.out.println("Player 2 is younger.");
			currentPlayer = 1;
		}
		*/
		
		// Prepare the game - draw cards and ask for swapped cards.
		prepare();
		
		// Start an initial turn.
		startTurn (rollDice(), rollDice(), rollDice());
		
		logger.setInitialState(this);
		
		// While the game is still running, keep query current player for action.
		while (!isGameOver ()) {
			
			queryForAction();

			gameOver = testGameOver ();
			
		}
		
		controller.showMessage("GG");
		
	}
	
	/**
	 * Deduct appropriate number points from whoever's turn it is.
	 * Set three dice.
	 * Call all cards Start Turn event
	 */
	public void startTurn(int die1, int die2, int die3) {
		Player player = players[currentPlayer];			
		int deduct = 0;
		int i = 0;
		for (i = 0; i < FIELD_SIZE; i++) {
			
			if (field.getCard(currentPlayer,i) == null) {
				
				deduct++;
				
			}
			
		}
		player.setVP(player.getVP() - deduct);
		
		/*for (i = 0; i < NUM_DIE; i++) {
			
			diceRolls[i] = rollDice();
			
		}*/
		
		// Set the die
		diceRolls[0] = die1;
		diceRolls[1] = die2;
		diceRolls[2] = die3;
		
		for (Card c : field.getAllCards()) {
			c.onTurnStart(visor, currentPlayer);
		}
		
		
		// Bring cards from the past into the future.
		List<TimeTravellingCard> newCards = getPendingFutureCards(turnNumber);
		
		for (TimeTravellingCard tc : newCards) {
			
			removePendingFutureCard(tc);
			
			// REmove the card currently in that position
			Card existingCard = getField().getCard(tc.getOwnerId(), tc.getPosition());
			if (existingCard != null) {
				System.out.println ("Card " + existingCard + " was overriden by " + tc.getTheCard().getName() + " from time travel!");
				getField().setCard(tc.getOwnerId(), tc.getPosition(), null);
				this.discard(existingCard); // ignores grim reaper
			}
			
			// Add the card in that position
			getField().setCard(tc.getOwnerId(), tc.getPosition(), tc.getTheCard());
			
			
		}
		
	
	}
	
	/**
	 * Perform all end-of-turn actions for all cards.
	 * Increment the current turn number, and swap the current player.
	 */
	public void nextTurn () {
		for (Card c : field.getAllCards()) {
			c.onTurnEnd(visor, currentPlayer);
		}
		currentPlayer = (currentPlayer + 1) % players.length;
		turnNumber ++;
	}
	
	public int getTurnNumber () {
		return turnNumber;
	}
	
	/**
	 * Query the current player for a single action.
	 */
	private void queryForAction() {
		/* player actions */
	
		Player player = players[currentPlayer];		
		
		controller.showScreen(player);
		
		IPlayerAction nextAction = controller.getAction(player);
		System.out.println("Action chosen: " + nextAction.getDescription());
		
		nextAction.query(visor);
		nextAction.execute(visor);
		

	}


	/**
	 * Prepare the player's initial state of the game (using controller for input)
	 */
	public void prepare () {
		
		// Initialise the deck.
		cardTypes.InitialiseCards(deck);
		deck.shuffle();
		
		// Draw four cards for each player.
		for (int i = 0; i < INITIAL_CARDS; i++) {
			
			players[0].addCard(drawCard());
			players[1].addCard(drawCard());

		}
		
		currentPlayer = 0;
		
		// For all players, query them for the cards they want to swap.
		for (int player = 0; player < MAX_PLAYERS; player++) {
			currentPlayer = player;
			
			swappedCards[player] = new ArrayList<Card>();
			while (swappedCards[player].size() < NUM_SWAP_CARDS) {
				Card swapped;
				while ((swapped = controller.getCard(players[currentPlayer].getHand(),
						"Player " + player + ": Please select a card to pass to your opponent (" + swappedCards[player].size()
						+ "/" + NUM_SWAP_CARDS + ")")) == null) {
					controller.showMessage ("Invalid card.");
				}
				
				
				addSwappedCard(player, swapped);
				
			}
		}
		
		// Perform the actual swap.
		swapAllCards();
		
		// For all players, query them for where to lay their initial cards.
		currentPlayer = 0;
		Player p;
		IPlayerAction layCard = new LayCardAction();
		for (currentPlayer = 0; currentPlayer < MAX_PLAYERS; currentPlayer ++) {
			p = players[currentPlayer];
			while (p.getHandSize() != 0) {
				layCard.execute(visor);
			}
		}
		
		currentPlayer = 0;
	}
	
	/**
	 * get this game's visor
	 */
	
	public GameVisor getGameVisor() {
		return visor;
	}
	
	/**
	 * For player, add 'c' to the list of cards that have been swapped
	 * and remove it from their hand.
	 * @param player player that swapped
	 * @param c card to swap
	 */
	public void addSwappedCard (int player, Card c) {
		swappedCards[player].add(c);
		players[currentPlayer].getHand().remove(c);
	}
	
	/**
	 * Swap all the cards in the list of swapped cards.
	 */
	public void swapAllCards() {
		for (int player = 0; player < MAX_PLAYERS; player++) {
			
			int otherPlayer = (player + 1) % MAX_PLAYERS;
			
			controller.showMessage("Player " + (player + 1) + " swapped " + swappedCards[player]);
			
			for (Card swappedCard : swappedCards[player]) {
				giveCard(player, otherPlayer, swappedCard);
			}
			
		}
	}
	
	
	public boolean isGameOver () {
		return gameOver;
	}
	
	/** 
	 * Test if the game is over,
	 * and store the result in gameOver
	 */
	public boolean testGameOver () {
		boolean gg;
		if (getVictoryStockpile() <= 0 || (players[0].getVP() <= 0 || players[1].getVP() <= 0)) {
			gg = true;
		} else {
			gg = false;
		}
		return gg;
	}
	
	public Date getBirthday(int playerNum) {
		
		String birthday;
		boolean isValid = false;
		Date playerBirthday = null;
		while (!isValid) {
			birthday = controller.getString("Please specify Player " + playerNum + "'s birthday (dd/mm/yyyy): ");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try {
				playerBirthday = sdf.parse(birthday);
				
				isValid = true;
				
			} catch (ParseException e) {
				
			}
			
			
			if (!isValid) {
				System.out.println("Invalid input.");
			}
		}
		
		
		System.out.println("Your birthday is on " + playerBirthday.toString());
		
		return playerBirthday;
		
	}

	public int rollDice() {
		
		return generator.randomInt(1,6);
	
	}

	// Get a card from the deck - might be null.
	// In future this may need to be public and a pointer to Game passed to certain classes
	// to facilitate game mechanics.
	public Card drawCard() {
		
		if (deck.isEmpty()) {
			
			deck = discardPile;
			deck.shuffle();
			discardPile = new DiscardPile();
			
		}
		return deck.getCard();
	
	}
	
	// Get the deck
	public Pile getDeck () {
		
		return deck;
	}
	
	public Field getField() {
		
		return field;
		
	}
	
	public Player getCurrentPlayer () {
		
		return players[currentPlayer];
	
	}
	
	public int whoseTurn() {
		
		return currentPlayer;
		
	}
	
	public void setWhoseTurn (int playerId) {
		
		currentPlayer = playerId;
		
	}
	
	public int[] getDiceRolls() {
		
		return diceRolls;
		
	}
	public void setDiceRolls (int[] rolls) {
		
		diceRolls = rolls;
		
	}
	public int getDiceRoll(int i) {
		int result = 0;
		if (i >= 0 && i < diceRolls.length) {
			return diceRolls[i];
		}
		
		return result;
	}
	
	public boolean hasDiceRoll (int roll) {
		boolean result = false;
		for (int i = 0; i < diceRolls.length; i++) {
			if (diceRolls[i] == roll) {
				result = true;
			}
		}
		
		return result;
	}
	
	
	public int getNumDiceRolls() {
		int numDice = 0;
		for (int i = 0; i < diceRolls.length; i++) {
			if (diceRolls[i] != 0) {
				numDice ++;
			}
		}
		return numDice;
	}
	
	public void setDiceRoll(int oldValue, int newValue) {
		boolean handled = false;
		for (int i = 0; i < Game.NUM_DIE && handled == false; i++) {
			if (diceRolls[i] == oldValue) {
				handled = true;
				diceRolls[i] = newValue;
			}
		}
	}
	
	public void useDice (int value) {
		
		boolean done = false;
		
		for (int i = 0; i < NUM_DIE && !done; i++) {
			
			if (diceRolls[i] == value) {
				
				diceRolls[i] = 0;
				done = true;
				
			}
			
		}
		
	}
	
	public IController getController() {
		
		return controller;
		
	}
	
	public void discard(Card c) {
		
		// doesn't need handler: discardPile calls it
		discardPile.addCardToFront(c);
		
	}
	
	
	public void giveCard(int currentPlayer, int targetPlayer, Card c) {
		players[targetPlayer].addCard(c);
		players[currentPlayer].getHand().removeElement(c);
		
	}

	public Player getPlayer (int player) {
		return players[player];
	}
	
	public Pile getDiscardPile() {
		
		return discardPile;
		
	}
	
	// the stockpile is equal to 36 - total number of consumed VPs
	public int getVictoryStockpile() {
		int vp = INITIAL_VP;
		for (int i = 0; i < players.length; i++) {
			vp -= players[i].getVP();
		}
		return vp;
	}
	
	/* Generate a list of possible actions for a player */
	public List<IPlayerAction> generateActions (Player p) {
		List<IPlayerAction> potentialActions = new ArrayList<IPlayerAction>();
		List<IPlayerAction> actions = new ArrayList<IPlayerAction>();
		GameVisor g = new GameVisor(this);
		
		potentialActions.add(new TakeMoneyAction());
		potentialActions.add(new TakeCardAction());
		potentialActions.add(new PlayCardAction());
		potentialActions.add(new ActivateCardAction(null));
		potentialActions.add(new ViewCardAction());
		potentialActions.add(new EndTurnAction());
		
		
		for (IPlayerAction action : potentialActions) {
			if (action.isVisible(g)) {
				actions.add(action);
			}
		}
				
		return actions;
	}
	
	/**
	 * Returns the list of cards that should appear in this turn.
	 * @param turn
	 * @return
	 */
	public List<TimeTravellingCard> getPendingFutureCards(int turn) {
		List <TimeTravellingCard> result = new ArrayList<TimeTravellingCard>();
		
		for (TimeTravellingCard tc : pendingFutureCards) {
			
			if (tc.getTurnNumber() == turn) {
				result.add(tc);
			}
			
		}
		
		return result;		
	}
	
	/**
	 * Remove the specified TimeTravellingCard from the collection.
	 * @param tc
	 */
	public void removePendingFutureCard(TimeTravellingCard tc) {
		
		pendingFutureCards.remove(tc);
		
	}
	
	/**
	 * Add the specified TimeTravellingCard to the collection.
	 * @param tc
	 */
	public void addPendingFutureCard(TimeTravellingCard tc) {
		
		pendingFutureCards.add(tc);
		
	}
	
	/**
	 * Gets this game's action log
	 * @return
	 */
	public ActionLogger getActionLogger() {
		
		return logger;
		
	}

}