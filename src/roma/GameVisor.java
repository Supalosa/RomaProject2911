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
	
	public Controller getController() {
		
		return game.getController();
		
	}
	
	public void discard(Card c) {
		
		game.discard (c);
		
	}
	
	public void giveCard(int currentPlayer, int targetPlayer, Card c) {
		
		game.giveCard(currentPlayer, targetPlayer, c);
		
	}
	
	public void setTurnEnded (boolean state) {
		game.setTurnEnded (state);
	}
	
	public Player getPlayer (int player) {
		return game.getPlayer(player);
	}
	
	public Card drawCard() {
		
		return game.drawCard();
	
	}
}
