package roma;

import java.text.*;
import java.util.*;

import cards.*;
import actions.*;
import modifiers.*;

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
	private List<Card>[] swappedCards;

	private int turnNumber;

	// List of all active modifiers on the cards
	private List<IModifier> activeModifiers;

	// Maps of Card -> Position for future cards to be placed
	private List<TimeTravellingCard> pendingFutureCards;

	// log of actions
	private ActionLogger logger;

	// list of immutable game states at start of each turn
	private List<ImmutableGameState> gameSnapshots;

	private boolean isTimeParadox;

	private GameVisor visor;

	public static final int FIELD_SIZE = 7;
	public static final int BRIBE_DISC = 7; // field position of bribe disc
	public static final int MAX_PLAYERS = 2;
	public static final int INITIAL_VP = 36;
	public static final int NUM_DIE = 3;

	public static final int INITIAL_CARDS = 5;
	public static final int NUM_SWAP_CARDS = 2; // number of cards to swap at
												// start of game

	public Game(IController controller) {

		generator = new RandomGenerator();
		this.controller = controller;
		gameOver = false;
		visor = new GameVisor(this);

		isTimeParadox = false;

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

		gameSnapshots = new ArrayList<ImmutableGameState>();

		activeModifiers = new ArrayList<IModifier>();
	}

	/**
	 * Automated loop that runs the game. Is only used when playing, NOT when
	 * testing.
	 */
	public void run() {
		// small change 2
		/*
		 * this is f-ing annoying plr1 = getBirthday(1); plr2 = getBirthday(2);
		 * 
		 * if (plr1.compareTo(plr2) > 0) { // player 1 is younger
		 * System.out.println("Player 1 is younger."); currentPlayer = 0; } else
		 * { System.out.println("Player 2 is younger."); currentPlayer = 1; }
		 */

		// Prepare the game - draw cards and ask for swapped cards.
		prepare();

		// Start an initial turn.
		startTurn(rollDice(), rollDice(), rollDice());

		// Take a snapshot of Turn 0
		takeSnapshot();
		// logger.setInitialState(this);

		// While the game is still running, keep query current player for
		// action.
		while (!isGameOver()) {

			queryForAction();

			gameOver = testGameOver();

		}

		controller.showMessage("GG");

	}

	public void takeSnapshot() {

		// System.out.println ("Take Snapshot Start: " + gameSnapshots.size());
		// Take a snapshot
		ImmutableGameState snapshot = new ImmutableGameState(this, turnNumber);

		// search for snapshots with same turn, delete
		// this allows us to add snapshots with impunity until we are sure that
		// the snapshot is in the final state
		// the reason we may not be sure it is final is because acceptance
		// testing does not set the dice until a seperate function call
		List<ImmutableGameState> toDelete = new ArrayList<ImmutableGameState>();
		for (ImmutableGameState state : gameSnapshots) {

			if (state.getTurnNumber() == turnNumber) {
				// System.out.println ("Found dupe gamestate turn " + turnNumber
				// + ", deleting");
				toDelete.add(state);
			}

		}

		// delete duplicates
		for (ImmutableGameState state : toDelete) {

			gameSnapshots.remove(state);

		}

		// add this one
		gameSnapshots.add(snapshot);

		// Load this snapshot (DEBUG) - if anything
		// goes wrong with acceptance with this line uncommented
		// then there is something very wrong with ImmutableGameStates

		// ACTUALLY Nvm - ImmutableGameStates is bugged wrt dice rollls
		// see if this fixes it?
		// getGameVisor().copyStateFrom(snapshot);

	}

	/**
	 * Deduct appropriate number points from whoever's turn it is. Set three
	 * dice. Call all cards Start Turn event
	 */
	public void startTurn(int die1, int die2, int die3) {
		Player player = players[currentPlayer];
		int deduct = 0;
		int i = 0;
		for (i = 0; i < FIELD_SIZE; i++) {

			if (field.getCard(currentPlayer, i) == null) {

				deduct++;

			}

		}
		player.setVP(player.getVP() - deduct);

		/*
		 * for (i = 0; i < NUM_DIE; i++) {
		 * 
		 * diceRolls[i] = rollDice();
		 * 
		 * }
		 */

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
			Card existingCard = getField().getCard(tc.getOwnerId(),
					tc.getPosition());
			if (existingCard != null) {
				System.out.println("Card " + existingCard
						+ " was overriden by " + tc.getTheCard().getName()
						+ " from time travel!");
				getField().setCard(tc.getOwnerId(), tc.getPosition(), null);
				this.discard(existingCard); // ignores grim reaper
			}

			// Add the card in that position
			getField().setCard(tc.getOwnerId(), tc.getPosition(),
					tc.getTheCard());

		}

		if (getNumSnapshots() < turnNumber - 1) {

			System.err.println("Incongruous snapshots, something was missed. "
					+ getNumSnapshots() + " snapshots but in turn "
					+ turnNumber);
			for (ImmutableGameState g : gameSnapshots) {
				System.err.print(g.getTurnNumber() + ", ");
			}

			System.err.println();
			try {
				throw new Exception("Incongruous Snapshot Count");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(1);
		}
	}

	/**
	 * Perform all end-of-turn actions for all cards. Increment the current turn
	 * number, and swap the current player.
	 */
	public void nextTurn() {
		for (Card c : field.getAllCards()) {
			c.onTurnEnd(visor, currentPlayer);
		}
		currentPlayer = (currentPlayer + 1) % players.length;
		turnNumber++;
	}

	public void setTurnNumber(int turn) {

		turnNumber = turn;

	}

	public int getTurnNumber() {
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

		// take a snapshot lah THIS NOT WORK
		if (nextAction.getDescription().equals(EndTurnAction.DESCRIPTION)) {
			takeSnapshot();
		}

		nextAction.execute(visor);

	}

	/**
	 * Prepare the player's initial state of the game (using controller for
	 * input)
	 */
	public void prepare() {

		// Initialise the deck.
		cardTypes.InitialiseCards(deck);
		deck.shuffle();

		// Draw four cards for each player.
		for (int i = 0; i < INITIAL_CARDS; i++) {

			players[0].addCard(drawCard());
			players[1].addCard(drawCard());

		}

		currentPlayer = 0;
		
		controller.showMessage("Both players have been given FOUR (4) cards.");
		
		controller.showMessage("They will now swap two cards each from their hands.");

		controller.showMessage("");
		
		// For all players, query them for the cards they want to swap.
		for (int player = 0; player < MAX_PLAYERS; player++) {
			currentPlayer = player;

			swappedCards[player] = new ArrayList<Card>();
			while (swappedCards[player].size() < NUM_SWAP_CARDS) {
				Card swapped;
				while ((swapped = controller
						.getCard(
								players[currentPlayer].getHand(),
								"Player "
										+ (player + 1)
										+ ": Please select a card to pass to your opponent ("
										+ swappedCards[player].size() + "/"
										+ NUM_SWAP_CARDS + ")")) == null) {
					controller.showMessage("Invalid card.");
				}

				addSwappedCard(player, swapped);

			}
		}

		// Perform the actual swap.
		swapAllCards();
		
		controller.showMessage("");
		
		controller.showMessage("Both will now decide where to allocate their four cards.");
		
		controller.showMessage("");
		
		// For all players, query them for where to lay their initial cards.
		currentPlayer = 0;
		Player p;
		IPlayerAction layCard = new LayCardAction();
		for (currentPlayer = 0; currentPlayer < MAX_PLAYERS; currentPlayer++) {
			p = players[currentPlayer];
			while (p.getHandSize() != 0) {
				controller.showField();
				layCard.query(getGameVisor());
				layCard.execute(visor);
			}
		}

		controller.showMessage("");
		
		currentPlayer = 0;
	}

	/**
	 * get this game's visor
	 */

	public GameVisor getGameVisor() {
		return visor;
	}

	/**
	 * For player, add 'c' to the list of cards that have been swapped and
	 * remove it from their hand.
	 * 
	 * @param player
	 *            player that swapped
	 * @param c
	 *            card to swap
	 */
	public void addSwappedCard(int player, Card c) {
		swappedCards[player].add(c);
		players[currentPlayer].getHand().remove(c);
	}

	/**
	 * Swap all the cards in the list of swapped cards.
	 */
	public void swapAllCards() {
		for (int player = 0; player < MAX_PLAYERS; player++) {

			int otherPlayer = (player + 1) % MAX_PLAYERS;

			controller.showMessage("Player " + (player + 1) + " swapped "
					+ swappedCards[player]);

			for (Card swappedCard : swappedCards[player]) {
				giveCard(player, otherPlayer, swappedCard);
			}

		}
	}

	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Test if the game is over, and store the result in gameOver
	 */
	public boolean testGameOver() {
		boolean gg;
		if (isTimeParadox || getVictoryStockpile() <= 0
				|| (players[0].getVP() <= 0 || players[1].getVP() <= 0)) {
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
			birthday = controller.getString("Please specify Player "
					+ playerNum + "'s birthday (dd/mm/yyyy): ");
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

		return generator.randomInt(1, 6);

	}

	// Get a card from the deck - might be null.
	// In future this may need to be public and a pointer to Game passed to
	// certain classes
	// to facilitate game mechanics.
	public Card drawCard() {

		if (deck.isEmpty()) {

			addDiscardToDeck();

		}

		return deck.getCard();

	}

	public void addDiscardToDeck() {

		/*
		 * for (int i = topCards.size() - 1; i >= 0; i--) {
		 * deck.addCardToFront(topCards.get(i)); }
		 */
		for (Card c : discardPile.asList()) {
			deck.addCard(c);
		}

		discardPile.emptyPile();
		deck.shuffle();

	}

	// Get the deck
	public Pile getDeck() {

		return deck;
	}

	public Field getField() {

		return field;

	}

	public Player getCurrentPlayer() {

		return players[currentPlayer];

	}

	public int whoseTurn() {

		return currentPlayer;

	}

	public void setWhoseTurn(int playerId) {

		currentPlayer = playerId;

	}

	public int[] getDiceRolls() {

		return diceRolls;

	}

	public void setDiceRolls(int[] rolls) {

		diceRolls = rolls;

		// HACK : for the last EndTurnAction, set the dice rolls! :)
		EndTurnAction lastEndTurnAction = null;

		lastEndTurnAction = logger.getLastEndTurnAction();

		if (lastEndTurnAction != null) {
			lastEndTurnAction.setDiceRolls(rolls);
		}
	}

	public int getDiceRoll(int i) {
		int result = 0;
		if (i >= 0 && i < diceRolls.length) {
			return diceRolls[i];
		}

		return result;
	}

	public boolean hasDiceRoll(int roll) {
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
				numDice++;
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

	public void useDice(int value) {

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

	public Player getPlayer(int player) {
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
	public List<IPlayerAction> generateActions(Player p) {
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
	 * 
	 * @param turn
	 * @return
	 */
	public List<TimeTravellingCard> getPendingFutureCards(int turn) {
		List<TimeTravellingCard> result = new ArrayList<TimeTravellingCard>();

		for (TimeTravellingCard tc : pendingFutureCards) {

			if (tc.getTurnNumber() == turn) {
				result.add(tc);
			}

		}

		return result;
	}

	/**
	 * Remove the specified TimeTravellingCard from the collection.
	 * 
	 * @param tc
	 */
	public void removePendingFutureCard(TimeTravellingCard tc) {

		pendingFutureCards.remove(tc);

	}

	/**
	 * Add the specified TimeTravellingCard to the collection.
	 * 
	 * @param tc
	 */
	public void addPendingFutureCard(TimeTravellingCard tc) {

		pendingFutureCards.add(tc);

	}

	/**
	 * Gets this game's action log
	 * 
	 * @return
	 */
	public ActionLogger getActionLogger() {

		return logger;

	}

	public void setActionLogger(ActionLogger al) {

		logger = al;

	}

	/**
	 * A time paradox occurred. End the game.
	 * 
	 */
	public void onTimeParadox() {

		//System.out.println("!!!! Time Paradox !!!!");
		this.isTimeParadox = true;
		// VP-causer loses his VP
		players[currentPlayer].setVP(0);

		field.clearField();
		testGameOver();
	}

	/**
	 * Called when the game starts. Basically creates a snapshot of the game at
	 * turn 0, after all preparation has been made.
	 */
	public void onGameStarted() {

		takeSnapshot();

	}

	/**
	 * Returns the list of modifiers active
	 */
	public List<IModifier> getModifiers() {
		return this.activeModifiers;
	}

	/**
	 * Returns the list of all modifiers casted ON the field position specified
	 */
	public List<IModifier> getModifiersOn(int ownerId, int pos) {
		List<IModifier> results = new ArrayList<IModifier>();

		for (IModifier mod : activeModifiers) {
			if (mod.getTargetOwnerId() == ownerId && mod.getTargetPos() == pos) {
				results.add(mod);
			}
		}

		return results;

	}

	/**
	 * Returns the list of all modifiers casted BY the field position specified
	 */
	public List<IModifier> getModifiersBy(int ownerId, int pos) {
		List<IModifier> results = new ArrayList<IModifier>();

		for (IModifier mod : activeModifiers) {
			if (mod.getCasterOwnerId() == ownerId && mod.getCasterPos() == pos) {
				results.add(mod);
			}
		}

		return results;

	}

	/**
	 * Adds a modifier to the list
	 */
	public void addModifier(IModifier mod) {

		this.activeModifiers.add(mod);

	}

	/**
	 * Clears all the modifiers
	 */
	public void clearModifiers() {

		this.activeModifiers.clear();

	}

	/**
	 * Deletes the specified modifier
	 */
	public void deleteModifier(IModifier mod) {

		this.activeModifiers.remove(mod);

	}

	/**
	 * Deletes all modifiers casted ON the field position specified
	 */
	public void deleteModifiersOn(int ownerId, int pos) {
		List<IModifier> toRemove = new ArrayList<IModifier>();

		for (IModifier mod : activeModifiers) {
			if (mod.getTargetOwnerId() == ownerId && mod.getTargetPos() == pos) {
				toRemove.add(mod);
			}
		}

		for (IModifier mod : toRemove) {

			activeModifiers.remove(mod);

		}

	}

	/**
	 * Deletes all modifiers casted BY the field position specified
	 */
	public void deleteModifiersBy(int ownerId, int pos) {
		List<IModifier> toRemove = new ArrayList<IModifier>();

		for (IModifier mod : activeModifiers) {
			if (mod.getCasterOwnerId() == ownerId && mod.getCasterPos() == pos) {
				toRemove.add(mod);
			}
		}

		for (IModifier mod : toRemove) {

			activeModifiers.remove(mod);

		}

	}

	/**
	 * Attempts to return the game state at the beginning of the specified turn.
	 * The game state will be at the point where dice have been rolled, VP have
	 * been deducted etc.
	 * 
	 * @param turn
	 *            The turn to start at
	 * @return The game state at the specified turn. Null if the game state did
	 *         not exist (turn in the future, etc)
	 */
	public ImmutableGameState getGameStateForTurn(int turn) {
		ImmutableGameState state = null;

		for (ImmutableGameState iterator : gameSnapshots) {

			if (iterator.getTurnNumber() == turn) {

				state = iterator;

			}

		}

		return state;
	}

	/**
	 * Load snapshots from the list. Should NOT be used in the main game, only
	 * for child games which are replays of the parent.
	 * 
	 * @param snapshots
	 */
	public void setGameSnapshots(List<ImmutableGameState> snapshots) {
		// gameSnapshots.addAll(snapshots);
		gameSnapshots = new ArrayList<ImmutableGameState>(snapshots);
	}

	/**
	 * Returns whether the game has entered a Time Paradox.
	 * 
	 * @return
	 */
	public boolean isTimeParadox() {

		return isTimeParadox;

	}

	/**
	 * Sets the TIme Paradox state (oh god i'm tired)
	 * 
	 * @param value
	 */
	public void setTimeParadox(boolean value) {

		this.isTimeParadox = value;

	}

	/**
	 * Sets theGame over(oh god i'm tired)
	 * 
	 * @param value
	 */
	public void setGameOver(boolean value) {

		gameOver = value;

	}

	/**
	 * Return the number of snapshots (i.e. the highest turn) in the game
	 * 
	 * @return
	 */
	public int getNumSnapshots() {

		return gameSnapshots.size();
	}

	/**
	 * Get an immutable list of all the snapshots for this game
	 * 
	 * @return
	 */
	public List<ImmutableGameState> getSnapshots() {

		return new ArrayList<ImmutableGameState>(gameSnapshots);

	}

}