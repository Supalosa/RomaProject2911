package roma;

import java.util.*;

import actions.*;
import cards.Card;
import cards.activators.*;

/**
 * Keeps a log of a game up to the current point.
 * Gives the ability to rebuild a game.
 * @author Randal
 * @author Peter
 *
 */
public class ActionLogger {

	private List<LoggedAction> actions;
	
	private Field initialField;
	private int[] initialDice;
	private Deck initialDeck;
	private DiscardPile initialDiscardPile;
	private int [] initialSestertii;
	private int [] initialVP;
	private Vector<Card>[] initialHands;
	
	private int initialPlayer;
	
	EndTurnAction lastEndTurnAction; // we keep track of this to store last turn's dice rolls

	
	@SuppressWarnings("unchecked")
	public ActionLogger() {
		actions = new ArrayList<LoggedAction>();
		initialSestertii = new int[Game.MAX_PLAYERS];
		initialVP = new int[Game.MAX_PLAYERS];
		
		initialSestertii[0] = -1;
		initialSestertii[1] = -1;
		
		initialVP[0] = -1;
		initialVP[1] = -1;
		
		initialHands = new Vector[Game.MAX_PLAYERS];
		
		initialPlayer = 0;
				
	}
	
	public void setInitialPlayer(int turn) {
		
		initialPlayer = turn;
		
	}
	public void setInitialDice(int[] rolls) {
		if (initialDice == null) { // only do this once...
			initialDice = rolls.clone();
		} else if (lastEndTurnAction != null) {
			
			lastEndTurnAction.setDiceRolls(rolls.clone());
			
		}
	}
	
	public void setInitialDeck(Deck d) { 
		
		initialDeck = (Deck)d.getCopy();
		
	}
	
	public void setInitialDiscard(DiscardPile d) { 
		
		initialDiscardPile = (DiscardPile)d.getCopy();

	}
	
	public void setInitialField(Field f) {
		if (initialField == null) { // only do this once
			initialField = new Field(null);
			// Get a copy of the field, into our initial state.
			for (int i = 0 ; i < Game.MAX_PLAYERS; i++) {
				for (int j = 0; j < Game.FIELD_SIZE; j++) {
					initialField.setCard(i, j, f.getCard(i, j));
				}
			}
		}
		
	}
	
	public void setInitialVP(int player, int amount) {
		
		if (initialVP[player] == -1) {
			initialVP[player] = amount;
		}
		
	}
	
	public void setInitialSestertii(int player, int amount) {
		
		if (initialSestertii[player] == -1) {
			initialSestertii[player] = amount;
		}
		
	}
	
	public void setInitialHand(int player, Vector<Card> hand) {
		
		if (initialHands[player] == null) {
			System.out.println ("ActionLogger:setInitialHand: " + hand.toString());
			initialHands[player] = (Vector<Card>) hand.clone();
		}
		
	}
	/**
	 * Copies the initial state of a game such that it
	 * can be rebuilt.
	 * @param g the game to copy
	 */
	public void setInitialState(Game g) {
		// Get sestertii and VP counts
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {
			
			setInitialSestertii(i,  g.getPlayer(i).getMoney());
			setInitialVP(i,  g.getPlayer(i).getVP());
			setInitialHand(i, g.getPlayer(i).getHand());
			
		}
		
		// Get a copy of the field, into our initial state.
		setInitialField(g.getField());
		
		// Get a copy of the first 3 dice rolls
		//initialDice = g.getDiceRolls().clone();
		setInitialDice(g.getDiceRolls());
		
		// Get a copy of the initial deck
		setInitialDeck((Deck)g.getDeck());
		
		// Get a copy of the initial deck
		setInitialDiscard((DiscardPile)g.getDiscardPile());
		
	}
	
	/**
	 * Inserts an action to the log.
	 * @param action The action to add
	 * @param turnNumber the current turn number of this action
	 */
	public void addAction(IPlayerAction action, int turnNumber) {
		
		actions.add(new LoggedAction(action, turnNumber));
		
		if (action.getDescription().equals("End Turn")) {
			lastEndTurnAction = (EndTurnAction)action; // we keep track of this to store last turn's dice rolls

		}
		
	}
	
	
	/**
	 * Rebuilds the game to a certain turn.
	 * The returned game will be at the start of the turn,
	 * after dice have been rolled and VP have been deducted.
	 * @param turn Start of turn number
	 * @return
	 */
	public Game rebuildGame(int turn) {
		
		//CardTypes library = new CardTypes();
		MockController mock = new MockController();
		Game newGame = new Game(mock);
		// set whose turn
		newGame.setWhoseTurn(initialPlayer);
		// set the initial dice rolls
		newGame.setDiceRolls(initialDice);
		System.out.println ("START rebuilding a game...");
		// set the vp, sestertii, hands
		for (int i = 0; i <Game.MAX_PLAYERS; i++) {
			
			newGame.getPlayer(i).setVP(initialVP[i]);
			newGame.getPlayer(i).setMoney(initialSestertii[i]);
			
			// give me a hand
			if (initialHands[i] != null) {
				for (int j = 0; j < initialHands[i].size(); j++) {
					Card newCard = initialHands[i].get(j).getCopy();
					newGame.getPlayer(i).getHand().insertElementAt(newCard, 0);
					System.out.println ("rebuildGame: inserted a " + newCard + " into Player " + i + "'s hand");
				}
			}
		}
		
		
		// build the deck - copy
		if (initialDeck != null) { // not empty
			for (Card c : initialDeck.asList()) {
				newGame.getDeck().addCard(c.getCopy());
			}
		}
		
		// build the discard - copy
		if (initialDiscardPile != null) {
			for (Card c : initialDiscardPile.asList()) {
				newGame.getDiscardPile().addCard(c.getCopy());
			}
		}
		
		// Build the field - copy
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {
			
			for (int j = 0; j < Game.FIELD_SIZE; j++) {
				
				Card originalCard = initialField.getCard(i,  j);
				if (originalCard != null) { // there was a card there
					Card newCard = originalCard.getCopy();
					newGame.getField().setCard(i, j, newCard);
				}
				
			}
			
		}
		
		// Game is now in initial state. Now rebuild from actions.
		for (LoggedAction act : actions) {
			if (act.turnNumber < turn) {
				System.out.println ("RebuildGame: Turn [" + act.turnNumber + "] executing " + act.action.getDescription() + "(" + act.action.describeParameters() + ")");
				act.action.execute(newGame.getGameVisor());
				if (newGame.getTurnNumber() == turn) {
					System.out.println("RebuildGame: reached start of turn " + turn);
					break;
				}
			} else {
				System.out.println ("RebuildGame: Reached end of turn");
			}
			
		}
				
		return newGame;
	
	}
	
	/**
	 * Inserts a card to the specified game, then replays to the current point.
	 * @param g The game to insert into
	 * @param turn The turn the specified game is at (MUST be at the beginning of the turn)
	 * @param c The card to insert
	 * @param ownerId the owner of the card
	 * @param pos which dice disc to insert it to (0-6)
	 * @return the new game
	 */
	public Game insertCardToGame(Game g, int turn, Card c, int ownerId, int pos) {

		System.out.println ("Inserting a " + c.getName() + " into the game...");
		Card replacedCard = g.getField().setCard(ownerId, pos, c);
		// Note: laying over the same card has NO effect (i.e. not even going to discard pile)
		if (replacedCard != null && replacedCard.getID() != c.getID()) {
			g.discard(replacedCard);
		}
		
		// find the appropriate action start
		//int turnNumber = 0;
		for (LoggedAction nextAction : actions) {
			IPlayerAction act = nextAction.action;
			System.out.println ("-- Game: " + g.getTurnNumber() + ", Action: " + nextAction.turnNumber);
			if (nextAction.turnNumber >= turn) {
				System.out.println ("insertCardToGame: executing " + act.getDescription() + "(" + act.describeParameters() + ")");
				System.out.println ("Player " + g.whoseTurn() + "'s hand: " + g.getCurrentPlayer().getHand());

				act.execute(g.getGameVisor());
				
			}
			
		}
		
		
		return g;
	}
	
	/**
	 * Returns whether two games are in identical states.
	 * TODO move to Game
	 * @param g1 first game to compare
	 * @param g2 second game to compare
	 * @return congruency of the two games
	 */
	public boolean isSameAs(Game g1, Game g2) {
		boolean result = true;
		
		if (g1.isGameOver() != g2.isGameOver()) {
			result = false;
		}
		
		if (g1.whoseTurn() != g2.whoseTurn()) {
			result = false;
		}
		
	
		
		
		
		return result;
	}
	
	private class LoggedAction {
		
		public final IPlayerAction action;
		public final int turnNumber;
		
		public LoggedAction(IPlayerAction action, int turnNumber) {
			
			this.action = action;
			this.turnNumber = turnNumber;
			
		}
	}
	
}
