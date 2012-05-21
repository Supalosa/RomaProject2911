package roma;

import java.util.*;

import actions.*;
import cards.Card;
import cards.activators.*;

/**
 * Keeps a log of a game up to the current point. Gives the ability to rebuild a
 * game.
 * 
 * @author Randal
 * @author Peter
 * 
 */
public class ActionLogger {

	private List<LoggedAction> actions;

	@SuppressWarnings("unchecked")
	public ActionLogger() {
		actions = new ArrayList<LoggedAction>();


	}


	/**
	 * Inserts an action to the log.
	 * 
	 * @param action
	 *            The action to add
	 * @param turnNumber
	 *            the current turn number of this action
	 */
	public void addAction(IPlayerAction action, int turnNumber) {

		actions.add(new LoggedAction(action, turnNumber));
		
	}

	/**
	 * Rebuilds the game to a certain turn. The returned game will be at the
	 * start of the turn, after dice have been rolled and VP have been deducted.
	 * 
	 * @param turn
	 *            Start of turn number
	 * @return
	 */
	public Game rebuildGame(int turn) {
		/*
		System.out.println("[" + this.hashCode()
				+ "] START rebuilding a game up to turn ..." + turn);
		// CardTypes library = new CardTypes();
		MockController mock = new MockController();
		Game newGame = new Game(mock);

		newGame.getActionLogger().setInitialState(this);
		// newGame.setActionLogger(this);
		// set whose turn
		newGame.setWhoseTurn(initialPlayer);
		System.out.println("Initially player " + newGame.whoseTurn()
				+ "'s turn");

		// set the initial dice rolls
		newGame.setDiceRolls(initialDice.clone());
		System.out.println("Initial dice rolls are: ");
		for (int i = 0; i < Game.NUM_DIE; i++) {

			System.out.println("[" + newGame.getDiceRoll(i) + " (init: "
					+ initialDice[i] + ")]");

		}

		// set the vp, sestertii, hands
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {

			newGame.getPlayer(i).setVP(initialVP[i]);
			newGame.getPlayer(i).setMoney(initialSestertii[i]);

			// give me a hand
			if (initialHands[i] != null) {
				for (int j = 0; j < initialHands[i].size(); j++) {
					Card newCard = initialHands[i].get(j).getCopy();
					newGame.getPlayer(i).getHand().add(newCard);
					System.out.println("rebuildGame: inserted a " + newCard
							+ " into Player " + i + "'s hand");
				}
			}
		}

		// build the deck - copy
		if (initialDeck != null) { // not empty
			for (Card c : initialDeck.asList()) {
				newGame.getDeck().addCard(c.getCopy());
				System.out.println("rebuildGame: inserted a " + c
						+ " into the deck [" + initialDeck.hashCode() + "]");
			}
		}

		// build the discard - copy
		/*if (initialDiscardPile != null) {
			for (Card c : initialDiscardPile.asList()) {
				newGame.getDiscardPile().addCard(c.getCopy());
				System.out.println("rebuildGame: inserted a " + c
						+ " into the discard pile [" + initialDiscardPile.hashCode() + "]");
			}
		}*/

		// Build the field - copy
		/*for (int i = 0; i < Game.MAX_PLAYERS; i++) {

			for (int j = 0; j < Game.FIELD_SIZE; j++) {
				if (initialField != null) { // else the field is empty
					Card originalCard = initialField.getCard(i, j);
					if (originalCard != null) { // there was a card there
						Card newCard = originalCard.getCopy();
						newGame.getField().setCard(i, j, newCard);
						System.out.println("rebuildGame: inserted a " + newCard
								+ " into the field at " + i + ", " + j);
					}
				}

			}

		}

		// Game is now in initial state. Now rebuild from actions.
		for (LoggedAction act : actions) {
			if (act.turnNumber < turn) {
				System.out.println("RebuildGame: Turn [" + act.turnNumber
						+ "] executing " + act.action.getDescription() + "("
						+ act.action.describeParameters() + ")");
				act.action.execute(newGame.getGameVisor());
				if (newGame.getTurnNumber() == turn) {
					System.out.println("RebuildGame: reached start of turn "
							+ turn);
					break;
				}
			} else {
				System.out.println("RebuildGame: Reached end of turn");
			}

		}

		return newGame;*/
		return null;

	}

	/**
	 * Inserts a card to the specified game, then replays to the current point.
	 * 
	 * @param g
	 *            The game to insert into
	 * @param turn
	 *            The turn the specified game is at (MUST be at the beginning of
	 *            the turn)
	 * @param c
	 *            The card to insert
	 * @param ownerId
	 *            the owner of the card
	 * @param pos
	 *            which dice disc to insert it to (0-6)
	 * @return the new game
	 */
	public Game insertCardToGame(Game g, int turn, Card c, int ownerId, int pos) {

		/*
		System.out.println("Inserting a " + c.getName() + " into the game...");
		Card replacedCard = g.getField().setCard(ownerId, pos, c);
		// Note: laying over the same card has NO effect (i.e. not even going to
		// discard pile)
		if (replacedCard != null && replacedCard.getID() != c.getID()) {
			g.discard(replacedCard);
		}

		// find the appropriate action start
		// int turnNumber = 0;
		for (LoggedAction nextAction : actions) {
			IPlayerAction act = nextAction.action;
			System.out.println("-- Game: " + g.getTurnNumber() + ", Action: "
					+ nextAction.turnNumber);
			if (nextAction.turnNumber >= turn) {
				System.out.println("Player " + g.whoseTurn() + "'s hand: "
						+ g.getCurrentPlayer().getHand());
				System.out.println("Dice Rolls: ");
				for (int i = 0; i < Game.NUM_DIE; i++) {

					System.out.print("[" + g.getDiceRoll(i) + "] ");

				}
				System.out.println();

				for (int i = 0; i < Game.MAX_PLAYERS; i++) {

					System.out.print("Field " + i + ": ");

					for (int j = 0; j < Game.FIELD_SIZE; j++) {
						Card thisCard;
						System.out.print(j + ": ");
						if ((thisCard = g.getField().getCard(i, j)) != null) {
							System.out.print(thisCard.getName());
						} else {
							System.out.print("Empty");
						}
						System.out.print(", ");

					}

					System.out.println();

				}

				System.out.println("insertCardToGame: executing "
						+ act.getDescription() + "(" + act.describeParameters()
						+ ")");

				try {

					act.execute(g.getGameVisor());

				} catch (NullPointerException e) {

					System.out.println("ERROR replaying: " + e.getMessage());
					e.printStackTrace();
					break;

				} catch (AssertionError e) {

					System.out.println("ERROR replaying: " + e.getMessage());
					e.printStackTrace();
					break;

				}
			}

			if (g.testGameOver()) {

				System.out.println("!!!! Game Over !!!!!");
				break;

			}

		}

		System.out.println("---- End of Replay! ----");
	
		return g;
		*/
		return null;
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
