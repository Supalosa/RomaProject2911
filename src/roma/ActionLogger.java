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
	 * Inserts a card to the specified ImmutableGameState, then replays to the current point.
	 * 
	 * @param g
	 *            The GameState to insert into
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
	public Game insertCardToGame(ImmutableGameState state, int turn, Card c, int ownerId, int pos) {

		
		System.out.println("Inserting a " + c.getName() + " into the gamestate...");
		MockController mock = new MockController();
		Game newGame = new Game(mock);
		
		newGame.getGameVisor().copyStateFrom(state);

		Card replacedCard = newGame.getField().setCard(ownerId, pos, c);
		// Note: laying over the same card has NO effect (i.e. not even going to
		// discard pile)
		if (replacedCard != null && replacedCard.getID() != c.getID()) {
			newGame.discard(replacedCard);
		}

		// find the appropriate action start
		// int turnNumber = 0;
		for (LoggedAction nextAction : actions) {
			IPlayerAction act = nextAction.action;
			
			if (nextAction.turnNumber >= turn) {
				System.out.println("-- Game: " + newGame.getTurnNumber() + ", Action: "
						+ nextAction.turnNumber);
				
				System.out.println("Player " + newGame.whoseTurn() + "'s hand: "
						+ newGame.getCurrentPlayer().getHand());
				System.out.println("Dice Rolls: ");
				for (int i = 0; i < Game.NUM_DIE; i++) {

					System.out.print("[" + newGame.getDiceRoll(i) + "] ");

				}
				System.out.println();

				for (int i = 0; i < Game.MAX_PLAYERS; i++) {

					System.out.print("Field " + i + ": ");

					for (int j = 0; j < Game.FIELD_SIZE; j++) {
						Card thisCard;
						System.out.print(j + ": ");
						if ((thisCard = newGame.getField().getCard(i, j)) != null) {
							System.out.print(thisCard.getName() + " (" + thisCard.getRealDefense() + ")");
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

					act.execute(newGame.getGameVisor());

				} catch (NullPointerException e) {

					System.out.println("ERROR replaying (NullPointerException): " + e.getMessage());
					e.printStackTrace();
					break;

				} catch (AssertionError e) {

					System.out.println("ERROR replaying (AssertionError): " + e.getMessage());
					e.printStackTrace();
					break;

				}
			}
			
			// Time Paradox
			if (newGame.testGameOver()) {

				System.out.println("!!!! Game Over !!!!!");
				break;

			}

		

		}

		System.out.println("---- End of Replay! ----");
	
		return newGame;
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
