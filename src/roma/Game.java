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
	private Controller controller;
	private boolean gameOver;
	
	private boolean endTurn;
	
	private GameVisor visor;
	
	public static final int FIELD_SIZE = 6;
	public static final int MAX_PLAYERS = 2;
	public static final int INITIAL_VP = 36;
	public Game(Controller controller) {
	
		generator = new RandomGenerator();
		this.controller = controller;
		gameOver = false;
		visor = new GameVisor(this);
		initGame();
		
	}
	
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
		prepare();
		
		while (!isGameOver ()) {
			step ();
			
			testGameOver ();
		}
		
		controller.showMessage("GG");
		
	}
	
	public void step() {

		Player player = players[currentPlayer];			
		endTurn = false;
		int deduct = 0;
		int i = 0;
		for (i = 0; i < 6; i++) {
			
			if (field.getCard(currentPlayer,i) == null) {
				
				deduct++;
				
			}
			
		}
		player.setVP(player.getVP() - deduct);
		
		for (i = 0; i < 3; i++) {
			
			diceRolls[i] = rollDice();
			
		}
		
		/* player actions */
		while (endTurn == false) {
			controller.showScreen(player);
			
			PlayerAction nextAction = controller.getAction(player);

			System.out.println("Action chosen: " + nextAction.getDescription());
			
			nextAction.execute(visor);
		
		}
		
		nextTurn ();
	}
	
	public void nextTurn () {
		currentPlayer = (currentPlayer + 1) % players.length;
	}
	
	public void initGame() {
		
		currentPlayer = 0;
		cardTypes = new CardTypes();

		players = new Player[2];
		players[0] = new Player(0);
		players[1] = new Player(1);
		
		deck = new Pile();
		cardTypes.InitialiseCards(deck);
		deck.shuffle();
		
		field = new Field();
		discardPile = new Pile();
		
		diceRolls = new int[3];
		
		
	}
	
	public void prepare () {
		
		
		for (int i = 0; i < 4; i++) {
			
			players[0].addCard(drawCard());
			players[1].addCard(drawCard());

		}
		
		currentPlayer = 0;
		controller.showHand(players[currentPlayer]);
		Card player11 = controller.getCard(players[currentPlayer], 
					"Please give the index of the 1st card to pass over to your opponent");
		Card player12 = controller.getCard(players[currentPlayer], 
					"Please give the index of the 2nd card to pass over to your opponent");
		while (player11== player12) {
			System.out.println("You have to select 2 different cards");
			player12 = controller.getCard(players[currentPlayer], 
						"Please give the index of the 2nd card to pass over to your opponent");
		}
		
		currentPlayer = (currentPlayer + 1) % players.length;
		controller.showHand(players[currentPlayer]);
		Card player21 = controller.getCard(players[currentPlayer], 
					"Please give the index of the 1st card to pass over to your opponent");
		Card player22 = controller.getCard(players[currentPlayer], 
					"Please give the index of the 2nd card to pass over to your opponent");
		while(player21 == player22) {
			System.out.println("You have to select 2 different cards");
			player22 = controller.getCard(players[currentPlayer], 
					"Please give the index of the 2nd card to pass over to your opponent");
		}
		
		giveCard(0, 1, player11);
		giveCard(0, 1, player12);
		giveCard(1, 0, player21);
		giveCard(1, 0, player22);
		
		currentPlayer = 0;
		Player p;
		PlayerAction layCard = new LayCardAction();
		for (currentPlayer = 0; currentPlayer < MAX_PLAYERS; currentPlayer ++) {
			p = players[currentPlayer];
			while (p.getHandSize() != 0) {
				layCard.execute(visor);
			}
		}
		
		currentPlayer = 0;
	}
	
	public boolean isGameOver () {
		return gameOver;
	}
	
	/** 
	 * Test if the game is over,
	 * and store the result in gameOver
	 */
	private void testGameOver () {
		if (getVictoryStockpile() <= 0 || (players[0].getVP() <= 0 || players[1].getVP() <= 0)) {
			gameOver = true;
		} else {
			gameOver = false;
		}
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
	
	public void useDice (int value) {
		
		boolean done = false;
		
		for (int i = 0; i < 3 && !done; i++) {
			
			if (diceRolls[i] == value) {
				
				diceRolls[i] = 0;
				done = true;
				
			}
			
		}
		
	}
	
	public Controller getController() {
		
		return controller;
		
	}
	
	public void discard(Card c) {
		
		discardPile.addCard(c);
		
	}
	
	public void giveCard(int currentPlayer, int targetPlayer, Card c) {
		
		players[targetPlayer].addCard(c);
		players[currentPlayer].getHand().removeElement(c);
		
	}
	
	public void setTurnEnded (boolean state) {
		endTurn = state;
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
	public List<PlayerAction> generateActions (Player p) {
		List<PlayerAction> potentialActions = new ArrayList<PlayerAction>();
		List<PlayerAction> actions = new ArrayList<PlayerAction>();
		GameVisor g = new GameVisor(this);
		
		potentialActions.add(new TakeMoneyAction());
		potentialActions.add(new TakeCardAction());
		potentialActions.add(new PlayCardAction());
		potentialActions.add(new ActivateCardAction());
		potentialActions.add(new ViewCardAction());
		potentialActions.add(new EndTurnAction());
		
		
		for (PlayerAction action : potentialActions) {
			if (action.isVisible(g)) {
				actions.add(action);
			}
		}
				
		return actions;
	}
	

}