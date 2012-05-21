package roma;

import java.util.Vector;

import cards.Card;

public class ImmutableGameState {
	private final Field field;
	private final int[] dice;
	private final Deck deck;
	private final DiscardPile discardPile;
	private final int[] sestertii;
	private final int[] VP;
	private final Vector<Card>[] hands;

	private final int player;

	private final int turnNumber;

	/**
	 * Constructs an immutable state of a game at a certain turn.
	 * 
	 * @param g
	 *            the game to copy
	 */
	@SuppressWarnings("unchecked")
	public ImmutableGameState(Game g, int turnNumber) {
		// Get sestertii and VP counts
		VP = new int[Game.MAX_PLAYERS];
		sestertii = new int[Game.MAX_PLAYERS];
		hands = new Vector[Game.MAX_PLAYERS];
		
		for (int i = 0; i < Game.MAX_PLAYERS; i++) {

			sestertii[i] = g.getPlayer(i).getMoney();
			VP[i] = g.getPlayer(i).getVP();
			Vector<Card> realHand = g.getPlayer(i).getHand();
			hands[i] = new Vector<Card>();
			hands[i].setSize(realHand.size());
			for (int j = 0; j < realHand.size(); j++) {
				hands[i].set(j, realHand.get(j).getCopy());
			}

		}

		// Get a copy of the field, into our  state.
		field = (Field) g.getField().clone();

		// Get a copy of the first 3 dice rolls
		dice = g.getDiceRolls().clone();

		// Get a copy of the  deck
		deck = (Deck) g.getDeck().getCopy();

		// Get a copy of the  deck
		discardPile = (DiscardPile) g.getDiscardPile().getCopy();

		// current player's turn
		player = g.whoseTurn();
		this.turnNumber = turnNumber;

	}

	public Field getField() {
		return field;
	}

	public int[] getDice() {
		return dice;
	}

	public Deck getDeck() {
		return deck;
	}

	public DiscardPile getDiscardPile() {
		return discardPile;
	}

	public int getSestertii(int player) {
		return sestertii[player];
	}

	public int getVP(int player) {
		return VP[player];
	}

	public Vector<Card> getHands(int player) {
		return hands[player];
	}

	public int getPlayer() {
		return player;
	}

	public int getTurnNumber() {
		return turnNumber;
	}
}
